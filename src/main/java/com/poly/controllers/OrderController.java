package com.poly.controllers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.AccountDAO;
import com.poly.dao.DiscountDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderItemDAO;
import com.poly.dto.*;
import com.poly.dto.enums.OrderStatusEnum;
import com.poly.models.Account;
import com.poly.models.Discount;
import com.poly.models.Order;
import com.poly.models.OrderItem;
import com.poly.services.*;

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
	AccountService accountService;
	@Autowired
	DiscountDAO dDAO;

	ObjectMapper objectMapper = new ObjectMapper();

	@GetMapping("/order")
	public String index(Model model) {
		Account acc = accountService.getAccountAuth();
		model.addAttribute("user", acc);
		model.addAttribute("cart", cart.getItems().values());
		model.addAttribute("amount", cart.getAmount());
		return "order";
	}

	@PostMapping("/order")
	public String payment1(Model model, @ModelAttribute("user") Account user) throws JsonProcessingException {
		LocalDateTime timeNow = LocalDateTime.now();
		Order order = new Order();
		order.setAccount(aDAO.findById(user.getUsername()).get());
		
		order.setMessage("meo meo meo meo");
		order.setOrderDate(timeNow);
		order.setAddress(user.getAddressDetail()+" ,"+user.getAddress());
		order.setStatus(OrderStatusEnum.PENDING);
		oDAO.save(order);
		for(ProductDTO item : cart.getItems().values()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);	
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(item.getProduct().getPrice());
			oiDAO.save(orderItem);
		}
		model.addAttribute("order", order);
		model.addAttribute("user", user);
		// model.addAttribute("totalAmount", getTotalAmount(order));
		return "order-success";
	}


		@GetMapping("/order/history")
	public String wait(Model model) {
		 model.addAttribute("user", accountService.getAccountAuth());
	 	 model.addAttribute("orderList", oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername()));
		return "order-history";
	}
		@GetMapping("/order/cancel")
	public String cancel(Model model) {
		 model.addAttribute("user", accountService.getAccountAuth());
	 	 model.addAttribute("orderList", oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername()));
		return "order-history-canceled";
	}
	@GetMapping("/order/shipping")
	public String shipping(Model model) {
		 model.addAttribute("user", accountService.getAccountAuth());
	 	 model.addAttribute("orderList", oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername()));
		return "order-history-shipping";
		
	}
	@GetMapping("/order/delyvering")
	public String delyvering(Model model) {
		 model.addAttribute("user", accountService.getAccountAuth());
	 	 model.addAttribute("orderList", oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername()));
		return "order-history-delyvering";
	}
		@GetMapping("/order/comfi")
	public String Comfi(Model model) {
		 model.addAttribute("user", accountService.getAccountAuth());
	 	 model.addAttribute("orderList", oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername()));
		return "order-history-comfi";
	}
		@GetMapping("/order/refun")
	public String refun(Model model) {
		 model.addAttribute("user", accountService.getAccountAuth());
	 	 model.addAttribute("orderList", oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername()));
		return "order-history-refun";
	}
		@GetMapping("/order/pending")
	public String pending(Model model) {
		 model.addAttribute("user", accountService.getAccountAuth());
	 	 model.addAttribute("orderList", oDAO.findOrderByUsername(accountService.getAccountAuth().getUsername()));
		return "order-history-pending";
	}
	public Double getTotalAmount(Order order) {
		List<OrderItem> orderItems = order.getOrderItems();
		double result = 0;
		for(OrderItem item : orderItems) {
			result += item.getPrice() * item.getQuantity();
		}
		return result;
	}
}
