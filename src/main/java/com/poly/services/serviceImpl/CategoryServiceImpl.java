package com.poly.services.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.CategoryDAO;
import com.poly.models.Category;
import com.poly.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDAO cDao;

    @Override
    public Category add(Category category) {
        Category existingCategory = cDao.getById(category.getCategoryId());
        if (existingCategory != null) {
            cDao.save(category);
            return category;
        }
        return null;
    }

    @Override
    public Category update(Category category) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int categoryId) {
        Category existingCategory = cDao.getById(categoryId);
        if (existingCategory != null) {
            cDao.deleteById(categoryId);
        }
    }

}
