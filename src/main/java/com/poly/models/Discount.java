package com.poly.models;

import jakarta.persistence.*;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.dto.DiscountType;

@Entity
@Table(name = "Discounts")
@Data
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discountid")
    private int discountId;

    @Enumerated(EnumType.STRING)
    @Column(name = "discounttype", nullable = false)
    private DiscountType discountType;

    @Column(name = "discountvalue", nullable = false)
    private Double discountValue;

    @Column(name = "discountinfo", columnDefinition = "TEXT DEFAULT ''")
    private String discountInfo;

    @Column(name = "maxusage", columnDefinition = "INT DEFAULT 1")
    private Integer maxUsage;

    @Column(name = "startdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    // @JsonFormat(pattern = "HH:mm:ss dd-MM-YYYY")
    private Timestamp startDate;

    @Column(name = "enddate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    // @JsonFormat(pattern = "HH:mm:ss dd-MM-YYYY")
    private Timestamp endDate;

    @Column(name = "productidlist", columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String productIdList;

    @Column(name = "isactive", columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(mappedBy = "discount")
    private List<Order> order;
}
