package com.poly.restcontrollers;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.poly.dao.CouponDAO;
import com.poly.models.Coupon;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/coupon")
public class CouponRestController {
    @Autowired
    CouponDAO cpDAO;

    @GetMapping
    public List<Coupon> findAll() {
        return cpDAO.findAll();
    }

    @GetMapping("/check/{couponCode}")
    public Coupon checkCoupon(@PathVariable("couponCode") String couponCode) {
        LocalDateTime now = LocalDateTime.now();

        Coupon coupon = cpDAO.findByCouponCode(couponCode);

        if (coupon != null) {
            Instant startInstant = coupon.getStartDate().toInstant();
            Instant endInstant = coupon.getEndDate().toInstant();

            LocalDateTime startDateTime = LocalDateTime.ofInstant(startInstant, ZoneId.systemDefault());
            LocalDateTime endDateTime = LocalDateTime.ofInstant(endInstant, ZoneId.systemDefault());

            if (now.isAfter(startDateTime) && now.isBefore(endDateTime)) {
                return coupon;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @PostMapping
    public void add(@RequestBody Coupon coupon) {
        coupon.setCouponCode(coupon.getCouponCode().toUpperCase().replace(" ", ""));
        cpDAO.save(coupon);
    }

    @PutMapping
    public void update(@RequestBody Coupon coupon) {
        coupon.setCouponCode(coupon.getCouponCode().toUpperCase().replace(" ", ""));
        cpDAO.save(coupon);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int couponId) {
        cpDAO.deleteById(couponId);
    }
}
