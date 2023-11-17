package com.poly.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Accounts")
@Data
public class Account {
    @Id
    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "addressdetail")
    private String addressDetail;

    @Column(name = "birthday")
    private Date birthDay;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "photo")
    private String photo;

    @Column(name = "role")
    private String role;

    @Column(name = "isbanned")
    private boolean isBanned;

    @Column(name = "reasonbanned")
    private String reasonBanned;

    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Order> orders;
    
    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Review> reviews;
    
    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Comment> comments;
}