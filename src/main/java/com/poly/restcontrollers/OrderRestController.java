package com.poly.restcontrollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.*;
import com.poly.dto.*;
import com.poly.dto.enums.OrderStatusEnum;
import com.poly.models.Account;
import com.poly.models.Order;
import com.poly.models.OrderItem;
import com.poly.services.AccountService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/order")
public class OrderRestController {
    @Autowired
    CategoryDAO cDAO;
    @Autowired
    ProductDAO pDAO;
    @Autowired
    ProductImageDAO piDAO;
    @Autowired
    OrderDAO oDAO;
    @Autowired
    OrderItemDAO oiDAO;
    @Autowired
    AccountDAO aDAO;
    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(oDAO.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(oDAO.findById(id).get());
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Order>> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(oDAO.findOrderByUsername(username));
    }

    @GetMapping("/purchase")
    public List<Order> userPurchase() {
        return oDAO.findOrderByUsername(getAccountAuth().getUsername());
    }
    

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/OrderStatus")
    public String orderStatus() throws IOException {
        return objectMapper.writeValueAsString(Arrays.asList(OrderStatusEnum.values()));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        if (order.getOrderId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        Order createdOrder = oDAO.save(order);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order updatedOrder) {
    Optional<Order> optionalOrder = oDAO.findById(id);

    if (optionalOrder.isPresent()) {
        Order existingOrder = optionalOrder.get();

        // Cập nhật trạng thái của đơn hàng
        existingOrder.setStatus(updatedOrder.getStatus());
        
        // Các bước cập nhật khác tùy thuộc vào yêu cầu của bạn

        oDAO.save(existingOrder); // Lưu lại đơn hàng đã cập nhật

        return ResponseEntity.ok(existingOrder);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if (!oDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        for (OrderItem orderItem : oiDAO.getByOrderId(id)) {
            oiDAO.delete(orderItem);
        }
        oDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete/{orderId}")
    public ResponseEntity<List<OrderItem>> removeOrderById(@PathVariable("orderId") int orderId) {
        List<OrderItem> oiList = new ArrayList<>();
        for (OrderItem oi : oiDAO.getByOrderId(orderId)) {
            ProductDTO pDTO = new ProductDTO();
            pDTO.setProduct(oi.getProduct());

            try {
                List<String> imageUrlList = pDAO.getImageUrlByProductId(oi.getProduct().getProductId());
                if (!imageUrlList.isEmpty()) {
                    oi.setProduct(pDTO.getProduct());
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            oiList.add(oi);
        }
        return ResponseEntity.ok(oiList);
    }
    public Account getAccountAuth() {
        return accountService.getAccountAuth();
    }

}
