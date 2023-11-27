package com.poly.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "Products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid")
    private int productId;

    @Column(name = "productname")
    private String productName;

    @Column(name = "price")
    private double price;

    @Column(name = "quantityinstorage")
    private int quantityInStorage;

    @Column(name = "subdescription")
    private String subDescription;

    @Column(name = "description")
    private String description;

    @Column(name = "storageinstruction")
    private String storageInstruction;

    @Column(name = "ingredient")
    private String ingredient;

    @Column(name = "producturl")
    private String productUrl;

    @ManyToOne
    @JoinColumn(name = "categoryid")
    private Category category;

    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "product")
    private List<SpecialOption> specialOptions;

    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "product")
    private List<SpecialOption> comments;

    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;
    
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
    
    @Column(name = "isavailable")
    private boolean isAvailable;

}
