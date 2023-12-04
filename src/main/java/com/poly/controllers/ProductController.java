package com.poly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.ProductImageDAO;

@Controller
public class ProductController {
	@Autowired
	ProductDAO pDAO;
	@Autowired
	CategoryDAO cDAO;
	@Autowired
	ProductImageDAO piDAO;

	@GetMapping("/products/")
	public String product(Model model) {
		// model.addAttribute("message", "Cart is empty, let's take a look at some
		// products!");
		return "product";
	}

	@GetMapping("/product/detail/{id}")
	public String product_detail(@PathVariable("id") int id, Model model) {
		model.addAttribute("product", pDAO.findById(id).orElse(null));
		return "product-detail";
	}

	@GetMapping("/product/")
	public String productDetailByName(@RequestParam("n") String productUrl, Model model) {
		model.addAttribute("product", pDAO.findByProductUrl(productUrl));
		return "product-detail";
	}
}
