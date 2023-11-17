package com.poly.controllers;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.regex.Pattern;

import org.hibernate.validator.constraints.CodePointLength.NormalizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.ProductImageDAO;
import com.poly.models.Product;
import com.poly.models.ProductImage;
import com.poly.utils.DiacriticsUtil;

@Controller
public class ProductController {
	@Autowired
	ProductDAO pDAO;
	@Autowired
	CategoryDAO cDAO;
	@Autowired
	ProductImageDAO piDAO;

	private final RestTemplate restTemplate;

	public ProductController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/products/")
	public String product(Model model) {
		// model.addAttribute("message", "Cart is empty, let's take a look at some
		// products!");
		return "product";
	}

	@GetMapping("/product/detail/{id}")
	public String product_detail(@PathVariable("id") int id, Model model) {
		model.addAttribute("product", pDAO.findById(id).get());
		return "product-detail";
	}

	@GetMapping("/product/")
	public String productDetailByName(@RequestParam("n") String productUrl, Model model) {
		model.addAttribute("product", pDAO.findByProductUrl(productUrl));
		return "product-detail";
	}
}
