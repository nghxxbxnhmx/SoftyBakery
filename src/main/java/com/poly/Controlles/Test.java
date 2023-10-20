package com.poly.Controlles;

import java.io.File;
import java.io.FileNotFoundException;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.*;
import com.poly.model.*;
import DB.UserUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Controller
public class Test {
	@Autowired CategoryDAO cDAO;
	@Autowired AccountDAO aDAO;
	@Autowired ProductDAO pDAO;
	@Autowired ProductImageDAO piDAO;
	@Autowired OrderDAO oDAO;
	@Autowired OrderItemDAO oiDAO;
	@Autowired ReviewDAO rDAO;
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// fix tạm thời lỗi đăng nhập
	@GetMapping("/test")
	public String test(Model model) throws ParseException {
		Account a = aDAO.getByUserName("user");
		a.setPassword(passwordEncoder().encode("123"));
		aDAO.save(a);
		return "test"; 
	} 




	public void saveAccount(Account a) {
		a.setAccountId(10004);
		a.setAddress("ở đâu còn lâu mới nói");
		a.setAdmin(false);
		a.setEmail("user@gmail.com");
		a.setFullName("user test");
		a.setPassword(passwordEncoder().encode("123"));
		a.setPhoneNumber("08888888");
		a.setUsername("user");
		aDAO.save(a);
	}

	public Account getAccountAuth() {
		return aDAO.getByUserName(UserUtils.getUser().getUsername());
	}

	public void testAccountInOrder() {
		Order o = oDAO.findById(10020).get();
		if (o.getAccount() == null) {
			System.out.println("ngu");
		} else {
			System.out.println(o.toString());
		}
	}

	public String saveAccountPhoto(String photo) {
		Account a = getAccountAuth();
		a.setPhoto(photo);
		aDAO.save(a);
		System.out.println(a.getPhoto());
		return a.getPhoto();
	}
	public void setPassword() {
		for(Account a : aDAO.findAll()) {
			a.setPassword(passwordEncoder().encode("123"));
			aDAO.save(a);
		}
		System.out.println("nghia ngu");
	}
}
