package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.models.OrderItem;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
	@Query("SELECT oi FROM OrderItem oi WHERE oi.order.orderId = ?1")
	List<OrderItem> getByOrderId(int orderId);
}

