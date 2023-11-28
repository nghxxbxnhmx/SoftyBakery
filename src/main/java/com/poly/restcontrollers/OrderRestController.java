package com.poly.restcontrollers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.dao.*;
import com.poly.dto.*;
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

@GetMapping("/pending")
public List<Order> getPendingOrders() {
    return oDAO.findPendingOrders();
}
@GetMapping("/cancel")
public List<Order> getCancelledOrders() {
    return oDAO.findCancelledOrders();
}

@GetMapping("/shipping")
public List<Order> getShippingOrders() {
    return oDAO.findShippingOrders();
}

@GetMapping("/dely")
public List<Order> getDelyveringOrders() {
    return oDAO.findDelyveringOrders();
}
@GetMapping("/comfi")
public List<Order> getConfirmedOrders() {
    return oDAO.findConfirmedOrders();
}
@GetMapping("/refun")
public List<Order> getRefundedOrders() {
    return oDAO.findRefundedOrders();
}
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Optional<Order> optionalOrder = oDAO.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        if (!oDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        order.setOrderId(id);
        Order updatedOrder = oDAO.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        if (!oDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        oDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemByOrderId(@PathVariable("orderId") int orderId) {
        List<OrderItem> oiList = new ArrayList<>();
        for (OrderItem oi : oiDAO.getOrderItemByOrderId(orderId)) {
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

    @GetMapping("/delete/{orderId}")
    public ResponseEntity<List<OrderItem>> removeOrderById(@PathVariable("orderId") int orderId) {
        List<OrderItem> oiList = new ArrayList<>();
        for (OrderItem oi : oiDAO.getOrderItemByOrderId(orderId)) {
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
