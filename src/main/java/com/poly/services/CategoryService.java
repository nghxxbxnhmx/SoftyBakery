package com.poly.services;

import org.springframework.stereotype.Service;

import com.poly.models.Category;

@Service
public interface CategoryService {
    Category add(Category category);

    Category update(Category category);

    void delete(int categoryId);
}
