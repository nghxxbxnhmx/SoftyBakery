package com.poly.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.dto.enums.AccountRoleEnum;

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

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "dd-MM-YYYY")
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

    @Column(name = "reset_code")
    private String resetCode;

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

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