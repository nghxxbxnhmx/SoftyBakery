package com.poly.dto;

import com.poly.models.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWithCount extends Product{
    int countProducts;
}
