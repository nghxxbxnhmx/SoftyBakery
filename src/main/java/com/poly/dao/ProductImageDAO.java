package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poly.models.ProductImage;

public interface ProductImageDAO extends JpaRepository<ProductImage, Integer> {
}
