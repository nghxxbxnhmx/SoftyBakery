package com.poly.models;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.dto.enums.OrderStatusEnum;

@Entity
@Table(name = "Orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private int orderId;

    // @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @Column(name = "message")
    private String message;

    @Column(name = "orderdate")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "HH:mm:ss YYYY-MM-dd")
    private Timestamp orderDate;

    @ManyToOne
    @JoinColumn(name = "discountid")
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "couponid")
    private Coupon coupon;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusEnum status;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
