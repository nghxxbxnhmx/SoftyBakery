package com.poly.controllers;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;


import org.hibernate.validator.constraints.CodePointLength.NormalizationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.ProductImageDAO;
import com.poly.dao.SpecialOptionDAO;
import com.poly.models.Product;
import com.poly.models.ProductImage;
import com.poly.models.SpecialOption;

@Controller
public class ProductController {
	@Autowired
	ProductDAO pDAO;
	@Autowired
	CategoryDAO cDAO;
	@Autowired
	ProductImageDAO piDAO;
	@Autowired
	SpecialOptionDAO sDAO;

	private final RestTemplate restTemplate;

	public ProductController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/product/")
	public String product(Model model) {
		// model.addAttribute("message", "Cart is empty, let's take a look at some products!");
		return "product";
	}

	// @GetMapping("/product/detail/{id}")
	// public String product_detail(@PathVariable("id") int id, Model model) {
	// 	model.addAttribute("product", pDAO.findById(id).get());
	// 	model.addAttribute("spe", sDAO.findById(id).get());
	// // 		List<String> specialOptions = sDAO.findOptionsByProductId(id);
 	// // model.addAttribute("spe", specialOptions);
	// 	return "product-detail";
	// }

	@GetMapping("/product/detail/{id}")
	public String product_detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("product", pDAO.findById(id).get());
	
		List<String> specialOptions = sDAO.findOptionsByProductId(id);
		model.addAttribute("spe", specialOptions);
	
		return "product-detail";
	}
	

// 	@GetMapping("/product/detail/{id}")
// public String product_detail(@PathVariable("id") int id, Model model) {
//     Product product = pDAO.findById(id).orElse(null);
//     if(product != null) {
//         model.addAttribute("product", product);
//         List<SpecialOption> specialOptions = sDAO.findByProductId(id);
//         model.addAttribute("spe", specialOptions);
//     }
//     return "product-detail";
// }
}
