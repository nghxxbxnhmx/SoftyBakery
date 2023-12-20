


package com.poly.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.AccountDAO;
import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.models.Category;
import com.poly.models.Product;
import com.poly.services.AccountService;


@Controller
public class HomeController {
	@Autowired ProductDAO pDAO;
	@Autowired AccountDAO aDAO;
	@Autowired CategoryDAO cDAO;
    @Autowired AccountService accountService;
    @RequestMapping("/")
    public String index2() {
        return "redirect:/home";
    }
    @RequestMapping("/home")
    public String index(Model model) {
    	List<Category> categories = new ArrayList<>();
		cDAO.findAll().stream().forEach((c) -> {
			if(pDAO.getProductByCategory(c.getCategoryId()).size()!=0) {
				c.setImageUrl(pDAO.getProductByCategory(c.getCategoryId()).get(0).getProductImages().get(0).getImageUrl());	
				categories.add(c);
			}
			
		});
    	model.addAttribute("c", categories);
    	model.addAttribute("count", pDAO.count());
    	model.addAttribute("p", pDAO.findAll());
		//top 10 product
		// model.addAttribute("top10", pDAO.getTop10Product());
		
    	printUserInfo();
        return "home";
    }  
    public void printUserInfo() {
        System.out.println(accountService.getAccountAuth()!=null?"Login successfully!":"No authentication!");
    }
    
    @GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/service")
	public String service() {
		return "service";
	}
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	// @GetMapping("/error")
    // public String error() {
    //     return "404";
    // }
}

