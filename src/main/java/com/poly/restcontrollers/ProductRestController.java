package com.poly.restcontrollers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.dao.*;
import com.poly.dto.*;
import com.poly.models.Product;
import com.poly.services.ProductService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rest/product")
public class ProductRestController {
	@Autowired
	ProductDAO pDAO;
	@Autowired
	CategoryDAO cDAO;
	@Autowired
	OrderItemDAO oiDAO;
	@Autowired
	ProductImageDAO piDAO;
	@Autowired
	ProductService productService;

	@GetMapping
	public List<ProductDTO> page() {
		List<ProductDTO> pDTOs = new ArrayList<>();
		for (Product p : pDAO.findAll()) {
			ProductDTO pDTO = new ProductDTO();
			pDTO.setProduct(p);
			pDTOs.add(pDTO);
		}
		return pDTOs;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") int id) {
		return ResponseEntity.ok(pDAO.findById(id).orElse(null));
	}

	@GetMapping("/c/{categoryId}")
	public ResponseEntity<List<Product>> findByCategory(@PathVariable("categoryId") int categoryId) {
		List<Product> products = pDAO.getProductByCategory(categoryId);
		return ResponseEntity.ok(products);
	}

	@PostMapping()
	public ResponseEntity<Product> post(@RequestBody Product product) {
		return ResponseEntity.ok(productService.add(product));
	}

	@PutMapping()
	public ResponseEntity<Product> put(@RequestBody Product product) {
		return ResponseEntity.ok(productService.update(product));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") int id) {
		productService.delete(id);
	}
}
