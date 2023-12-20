package com.poly.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.dao.ProductImageDAO;
import com.poly.models.Product;
import com.poly.models.ProductImage;


@Controller
public class AdminController {
	@Autowired ProductDAO pDAO;
	@Autowired CategoryDAO cDAO;
	@Autowired ProductImageDAO piDAO;
		@GetMapping("/admin")
	public String pageadmin() {
		return "admin/main";
	}
	@GetMapping("/admin/product")
	public String admin_product(Model model) {
		return "admin/admin-product";
	}
	@GetMapping("/admin/order")
	public String admin_order(Model model) {
		
		return "admin/admin-order";
	}
	@GetMapping("/admin/category")
	public String adminCategory(Model model) {

		return "admin/admin-category";
	}
	@GetMapping("/admin/account")
	public String adminAccount(Model model) {
		
		return "admin/admin-account";
	}

	
	
	@GetMapping("/admin/pi/{id}")
	public String index(@PathVariable(name = "id", required = false) int id, Model model) {
		Product product = pDAO.findById(id).orElse(null);
		
		model.addAttribute("product",product);
		return "admin/admin-product-image";
	}
	@GetMapping("/admin/pi/delete/{id}")
	public String delete_product_image(@PathVariable("id") int id, Model model) {
		Product p = piDAO.findById(id).get().getProduct();
		piDAO.deleteById(id);
		return "redirect:/admin/pi/" + p.getProductId();
	}
	@PostMapping("/admin/pi/{id}")
	public String put_product_image(@PathVariable("id") int id, Model model, @RequestParam("file") MultipartFile file) {
		try {
			String UPLOAD_DIR = "src\\main\\resources\\static\\images";
			Product p = pDAO.getById(id);
			ProductImage pimg = new ProductImage();
		    
		    if(!file.isEmpty()) {
	            	Path uploadDir = Paths.get(UPLOAD_DIR);
	            	Path filePath = uploadDir.resolve(file.getOriginalFilename());
					Files.createDirectories(uploadDir);
			        Files.write(filePath, file.getBytes());
			        
			        pimg.setProduct(p);
			        pimg.setImageUrl(file.getOriginalFilename());
	        }
		    piDAO.save(pimg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/pi/" + id;
	}

	@GetMapping("/manage/coupon")
	public String manage_coupon() {
		return "manage/manage-coupon";
	}

	@GetMapping("/admin/productimage")
	public String productImage(Model model) {

		model.addAttribute("products", pDAO.findAll());
		return "admin/admin-main-product-image";
	}
	@GetMapping("/admin/chart")
	public String chart() {
		return "admin/admin-chart";
	}
}
