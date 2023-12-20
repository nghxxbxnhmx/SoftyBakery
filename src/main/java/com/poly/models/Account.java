package com.poly.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.dto.enums.AccountRoleEnum;

@Entity
@Table(name = "Accounts")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    @Id
    @Column(name = "username")
    private String username;

    // @JsonIgnore
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

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthDay;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "photo")
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "ENUM('USER', 'MANAGER', 'ADMIN', 'SUPER_ADMIN')")
    private AccountRoleEnum role;

    @Column(name = "isbanned")
    private boolean isBanned;

    @Column(name = "reasonbanned")
    private String reasonBanned;

    @JsonIgnore(value = true)
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Order> orders;

    @JsonIgnore(value = true)
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Review> reviews;

    @JsonIgnore(value = true)
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Comment> comments;
}