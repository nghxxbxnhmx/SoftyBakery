package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.models.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	@Override
	@Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
	List<Order> findAll();

	@Query("SELECT o FROM Order o WHERE account.username = ?1 ORDER BY orderId DESC")
	List<Order> findOrderByUsername(String username);
	
    @Query("SELECT DISTINCT o FROM Order o JOIN o.orderItems oi JOIN oi.product p WHERE p.productId = :productId AND o.account.username = :username")
    List<Order> findByProductIdAndUsername(@Param("productId") int productId, @Param("username") String username);
}
  