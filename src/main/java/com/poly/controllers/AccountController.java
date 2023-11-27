package com.poly.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.AccountDAO;
import com.poly.models.Account;
import com.poly.services.AccountService;
import com.poly.utils.PasswordUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AccountController {
	@Autowired AccountDAO aDAO;
	@Autowired AccountService accountService;
	@GetMapping("/register")
	public String register(Model model, HttpServletRequest request, HttpServletResponse response) {
		Account a = new Account();
		model.addAttribute("user",a);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(getAccountAuth()!=null) {
			 new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "register";
	}
	@PostMapping("/register")
	public String register1(Model model, @ModelAttribute("user") Account a) {
		a.setPassword(PasswordUtil.encode(a.getPassword()));
		a.setPhoto("noImage.jpg");
		aDAO.save(a);
		return "login";
	}
	@GetMapping("/login")
	public String login() {

		return "login";
	}
	@GetMapping("/login/")
	public String loginValidation(@RequestParam("error") Boolean error, Model model) {
		if(error == true) {
				String message = "Sai tên đăng nhập hoặc mật khẩu!";
				model.addAttribute("message",message);
				return "login";
			}
		return "login";
	}
	
	@GetMapping("/profile")
	public String profile(Model model) {
		Account a = getAccountAuth();
		
		model.addAttribute("user", a);
		return "profile";
	}
	@GetMapping("/profile/{username}")
	public String profileUser(@PathVariable("username") String username,Model model) {
		Account a = aDAO.getById(username);
		
		model.addAttribute("user", a);
		return "profile";
	}
	

	@GetMapping("/profile/edit")
	public String editProfile(Model model) {
		return "profile-edit";
	}
	private static final String CLIENT_ID = "0ab0fb9877708c6";
    private static final String CLIENT_SECRET = "d55ae0d35f1f889cd6be96aca5684032c59ac50a";
    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/upload";

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam("file") MultipartFile file, Model model) {
        return "redirect:/profile";
    }

	@GetMapping("/order-history")
	public String orderhistory(Model model){
		Account a = getAccountAuth();
		
		model.addAttribute("user", a);
		return "order-history";
	}


	public Account getAccountAuth() { 
		return accountService.getAccountAuth();
	}
}
