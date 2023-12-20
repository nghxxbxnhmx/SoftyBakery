package com.poly.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.models.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	@Query("SELECT pi.imageUrl FROM ProductImage pi WHERE pi.product.productId = ?1")
	List <String> getImageUrlByProductId(int productId);

	@Query("SELECT p FROM Product p WHERE p.category.categoryId = ?1")
    List <Product> getProductByCategory(int categoryId);   
	
	@Query("SELECT p FROM Product p WHERE p.productUrl = ?1")
    Product findByProductUrl(String productUrl);   
	// New method to find a product by its name
    @Query("SELECT p FROM Product p WHERE p.productName = ?1")
    Product findByProductName(String productName);
	// top 10 product
    @Query(value = "SELECT p.ProductID,p.ProductName,c.CategoryName,i.ImageURL,c.CategoryID,p.Price,count(*) AS Count FROM products p\r\n"
    		+ "LEFT JOIN orderitems o ON o.ProductID = p.ProductID\r\n"
    		+ "LEFT JOIN categories c ON c.CategoryID = p.CategoryID\r\n"
    		+ "LEFT JOIN productimages i ON i.ProductID = p.ProductID\r\n"
    		+ "GROUP BY p.ProductID\r\n"
    		+ "ORDER BY Count DESC\r\n"
    		+ "LIMIT 10;", nativeQuery = true)
	List<Map<String, Object>> getTop10Product();
}