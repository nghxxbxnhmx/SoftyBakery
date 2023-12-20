package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.dto.enums.OrderStatusEnum;
import com.poly.models.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	@Override
	@Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
	List<Order> findAll();

	@Query("SELECT o FROM Order o WHERE account.username = ?1 ORDER BY orderId DESC")
	List<Order> findOrderByUsername(String username);

	@Query("SELECT DISTINCT o FROM Order o JOIN o.orderItems oi JOIN oi.product p WHERE p.productId = :productId AND o.account.username = :username")
	List<Order> findByProductIdAndUsername(@Param("productId") int productId, @Param("username") String username);
	@Query("SELECT YEAR(o.orderDate) AS year, MONTH(o.orderDate) AS month, " +
			"COUNT(DISTINCT o.id) AS totalOrders, " +
			"SUM(oi.quantity * oi.price) AS totalRevenue " +
			"FROM Order o " +
			"JOIN o.orderItems oi " +
			"WHERE o.status = :status " +
			"GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) " +
			"ORDER BY year DESC, month DESC")
	List<Object[]> getOrderStatsByMonth(@Param("status") OrderStatusEnum status);

	@Query("SELECT YEAR(o.orderDate) AS year, MONTH(o.orderDate) AS month, " +
			"COUNT(DISTINCT o.id) AS totalOrders, " +
			"SUM(oi.quantity * oi.price) AS totalRevenue " +
			"FROM Order o " +
			"JOIN o.orderItems oi " +
			"GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) " +
			"ORDER BY year DESC, month DESC")
	List<Object[]> getOrderStatsByMonth();

}
