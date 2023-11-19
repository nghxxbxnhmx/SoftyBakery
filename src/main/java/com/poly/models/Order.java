package com.poly.models;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.dto.enums.OrderStatusEnum;

@Entity
@Table(name = "Orders")
@Data
public class Order {
  
 //  private static final int serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderid")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @Column(name = "message")
    private String message;

    @Column(name = "orderdate")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "discount")
    private Discount discount;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "couponid")
    private Coupon coupon;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusEnum status;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;
}

