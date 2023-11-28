package com.poly.models;

import jakarta.persistence.*;

import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "discounts")
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discountid")
    private int discountId;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "enddate")
    private Date endDate;
}
