package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.models.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	@Override
	@Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
	List<Order> findAll();

	@Query("SELECT o FROM Order o WHERE account.username = ?1")
	List<Order> findOrderByUsername(String username);
	
	//String insertQuery = "INSERT INTO reviews (username, comment, productid, rating, reviewdate, OrderID) VALUES (?, ?, ?, ?, ?, ?)";

}
  