package com.poly.models;

import jakarta.persistence.*;

import lombok.Data;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "Comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentid")
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

    

//    @Column(name = "rating")
//    private int rating;

    @Column(name = "commentcontent")
    private String commentContent;
@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss" , shape  = Shape.STRING) 
    @Column(name = "commentdate")
    private Date commentDate;


    @Column(name = "parentcommentid")
    private int parentCommentId;
}
