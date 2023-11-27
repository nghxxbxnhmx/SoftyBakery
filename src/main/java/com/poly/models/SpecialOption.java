package com.poly.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "specialoptions")
@Data
public class SpecialOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialoptionid")
    private int specialOptionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name = "option")
    private String option;
}
