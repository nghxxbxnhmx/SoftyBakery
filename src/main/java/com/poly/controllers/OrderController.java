package com.poly.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.AccountDAO;
import com.poly.dao.CouponDAO;
import com.poly.dao.DiscountDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderItemDAO;
import com.poly.dto.*;
import com.poly.dto.enums.OrderStatusEnum;
import com.poly.models.Order;
import com.poly.models.OrderItem;
import com.poly.services.*;
import com.poly.utils.MailerUtil;

@Controller
public class OrderController {
	@Autowired
	CartService cart;
	@Autowired
	AccountDAO aDAO;
	@Autowired
	OrderDAO oDAO;
	@Autowired
	OrderItemDAO oiDAO;
	@Autowired
	CouponDAO cpDAO;
	@Autowired
	AccountService accountService;
	@Autowired
	DiscountDAO dDAO;

	ObjectMapper objectMapper = new ObjectMapper();

	@GetMapping("/order")
	public String index(Model model) {
		Order order = new Order();
		order.setAccount(accountService.getAccountAuth());

		model.addAttribute("orderForm", order);
		model.addAttribute("cart", cart.getItems().values());
		model.addAttribute("amount", cart.getAmount());
		return "order";
	}

	@PostMapping("/order")
	public String payment1(Model model, @ModelAttribute("orderForm") Order order) throws IOException {
		LocalDateTime timeNow = LocalDateTime.now();

		order.setAddress(order.getAccount().getAddressDetail() + " ," + order.getAccount().getAddress());
		order.setAccount(aDAO.findById(order.getAccount().getUsername())
				.orElseThrow(() -> new IllegalArgumentException("Account not found")));
		order.setOrderDate(Timestamp.valueOf(timeNow));
		order.setStatus(OrderStatusEnum.PENDING);
		if(order.getCoupon().getCouponCode() != null) {
			order.setCoupon(cpDAO.findByCouponCode(order.getCoupon().getCouponCode()));
		}
		List<OrderItem> orderItems = new ArrayList<>();
		for (ProductDTO item : cart.getItems().values()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(item.getProduct().getPrice());
			orderItems.add(orderItem);
		}
		order.setOrderItems(orderItems);
		// Kiểm tra orderItems trước khi lưu
		if (!orderItems.isEmpty()) {
			oDAO.save(order);
			oiDAO.saveAll(orderItems);
		}
		
		model.addAttribute("amount", cart.getAmount());
		model.addAttribute("order", order);

		MailerUtil.send();

		cart.clear();
		return "order-success";
	}

	@GetMapping("/user/purchase")
	public String user_purchase(Model model) throws JsonProcessingException {
		model.addAttribute("user", accountService.getAccountAuth());
		model.addAttribute("orderList", oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername()));
		System.out.println(objectMapper.writeValueAsString(oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername())));
		return "order-history";
	}

	public Double getTotalAmount(Order order) {
		List<OrderItem> orderItems = order.getOrderItems();
		double result = 0;
		for (OrderItem item : orderItems) {
			result += item.getPrice() * item.getQuantity();
		}
		return result;
	}
}
