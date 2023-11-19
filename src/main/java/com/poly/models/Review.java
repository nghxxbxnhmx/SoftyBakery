package com.poly.models;

import jakarta.persistence.*;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "reviews")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewid")
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name = "rating")
    private int rating;
    
    @Column(name = "orderid")
    private String orderid;
    
    @Column(name = "comment")
    private String comment;
@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss" , shape  = Shape.STRING) 
    @Column(name = "reviewdate")
    private Date reviewDate;
}
