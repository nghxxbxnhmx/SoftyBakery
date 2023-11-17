package com.poly.models;

import jakarta.persistence.*;

import lombok.Data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(name = "commentcontent")
    private String commentContent;

    @Column(name = "commentdate")
    private Date commentDate;

    @Column(name = "parentcommentid")
    private int parentCommentId;
}
