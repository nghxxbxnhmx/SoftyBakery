package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.models.Coupon;

public interface CouponDAO extends JpaRepository<Coupon,Integer> {
    @Query("SELECT cp FROM Coupon cp WHERE cp.couponCode = ?1")
    Coupon findByCouponCode(String couponCode);
}
