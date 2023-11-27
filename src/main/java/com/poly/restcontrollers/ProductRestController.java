package com.poly.restcontrollers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.*;
import com.poly.dto.*;
import com.poly.models.Product;
import com.poly.services.ProductService;
import com.poly.utils.JsonReaderUtil;

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
	public ResponseEntity<List<ProductDTO>> page() {
		List<ProductDTO> pDTOs = pDAO.findAll().stream()
				.map(p -> {
					ProductDTO pDTO = new ProductDTO();
						pDTO.setProduct(p);
					return pDTO;
				})
				.collect(Collectors.toList());
		return ResponseEntity.ok(pDTOs);
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

	// pagination - pagination - pagination - pagination - pagination - pagination
	// @GetMapping("/")
	// public ResponseEntity<List<ProductDTO>> pageProduct(Model model,
	// 		@RequestParam(required = false, name = "c") String categoryId,
	// 		@RequestParam(required = false, name = "page", defaultValue = "0") Integer page,
	// 		@RequestParam(required = false, name = "size", defaultValue = "4") Integer size,
	// 		@RequestParam(required = false, name = "sort", defaultValue = "ASC") String sort,
	// 		@RequestParam(required = false, name = "key") String key,
	// 		@RequestParam(required = false) Double minPrice,
	// 		@RequestParam(required = false) Double maxPrice) {
	// 	if (size <= 0) {
	// 		size = 4;
	// 	}
	// 	if (!sort.equals("ASC") && !sort.equals("DESC")) {
	// 		sort = "ASC";
	// 	}
	// 	List<Product> products = pDAO.findAll();
	// 	List<ProductDTO> prdDTOList = new ArrayList<>();

	// 	Page<Product> filteredProducts = filterAndPaginateProducts(products, key, minPrice, maxPrice, sort, page, size,
	// 			categoryId);

	// 	for (Product p : filteredProducts) {
	// 		ProductDTO pDTO = new ProductDTO();
	// 		pDTO.setProduct(p);
	// 		try {
	// 			List<String> imageUrlList = pDAO.getImageUrlByProductId(p.getProductId());
	// 			if (!imageUrlList.isEmpty()) {
	// 				pDTO.setImageUrl(imageUrlList.get(0));
	// 			}
	// 		} catch (IndexOutOfBoundsException e) {
	// 			e.printStackTrace();
	// 		}

	// 		prdDTOList.add(pDTO);
	// 	}

	// 	return ResponseEntity.ok(prdDTOList);
	// }

	// private List<Product> filterByName(List<Product> products, String name) {
	// 	return products.stream().filter(product -> product.getProductName().toLowerCase().contains(name.toLowerCase()))
	// 			.collect(Collectors.toList());
	// }

	// private List<Product> filterByPriceRange(List<Product> products, double minPrice, double maxPrice) {
	// 	return products.stream().filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
	// 			.collect(Collectors.toList());
	// }

	// private List<Product> filterByCategory(List<Product> products, int categoryId) {
	// 	return products.stream().filter(product -> product.getCategory().getCategoryId() == categoryId)
	// 			.collect(Collectors.toList());
	// }

	// private List<Product> sort(List<Product> products, boolean ascending) {
	// 	Comparator<Product> priceComparator = Comparator.comparingDouble(Product::getPrice);
	// 	if (!ascending) {
	// 		priceComparator = priceComparator.reversed();
	// 	}
	// 	return products.stream().sorted(priceComparator).collect(Collectors.toList());
	// }

	// private Page<Product> filterAndPaginateProducts(List<Product> products, String name, Double minPrice,
	// 		Double maxPrice, String sort, int page, int size, String categoryId) {
	// 	List<Product> filteredProducts = products;

	// 	if (name != null && !name.isEmpty()) {
	// 		filteredProducts = filterByName(filteredProducts, name);
	// 	}
	// 	if (minPrice != null && maxPrice != null && minPrice >= 0 && maxPrice >= 0 && maxPrice >= minPrice) {
	// 		filteredProducts = filterByPriceRange(filteredProducts, minPrice, maxPrice);
	// 	}
	// 	if (categoryId != null && !categoryId.isEmpty()) {
	// 		filteredProducts = filterByCategory(filteredProducts, Integer.parseInt(categoryId));
	// 	}
	// 	if (sort.equals("ASC")) {
	// 		filteredProducts = sort(filteredProducts, true);
	// 	} else if (sort.equals("DESC")) {
	// 		filteredProducts = sort(filteredProducts, false);
	// 	}

	// 	Pageable pageable = PageRequest.of(page, size);
	// 	int totalItems = filteredProducts.size();
	// 	int startIndex = (int) pageable.getOffset();
	// 	int endIndex = Math.min(startIndex + size, totalItems);
	// 	List<Product> items = filteredProducts.subList(startIndex, endIndex);

	// 	return new PageImpl<>(items, pageable, totalItems);
	// }

}
