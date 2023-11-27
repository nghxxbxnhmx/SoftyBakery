package com.poly.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poly.models.Discount;
import com.poly.models.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO{
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Product product;
	private int quantity = 1;
	// private Discount discount;
}
