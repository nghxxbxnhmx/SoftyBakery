package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.Order;
import com.poly.model.OrderItem;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	@Query("SELECT o.orderId FROM Order o ORDER BY o.orderId DESC LIMIT 1")
    Integer findTopOrderId();
	
	@Query("SELECT o.orderId FROM Order o ORDER BY o.orderId DESC LIMIT 1")
    Integer getAccountIdByOrderId();
	
	
	@Override
	@Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
	List<Order> findAll();
     
	// @Override
	// @Query("SELECT o FROM Order where o.accountID = ?1 jpql")
	//hello

}
