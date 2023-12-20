package com.poly.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.dao.AccountDAO;
import com.poly.dto.AccountToUserDetail;
import com.poly.dto.enums.AccountRoleEnum;
import com.poly.models.Account;
import com.poly.models.Product;
import com.poly.models.ProductImage;
import com.poly.services.AccountService;
import com.poly.utils.PasswordUtil;
import com.poly.utils.RandomStringUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AccountController {
	@Autowired
	AccountDAO aDAO;
	@Autowired
	AccountService accountService;

	@GetMapping("/register")
	public String register(Model model, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {
		Account a = new Account();
		model.addAttribute("user", a);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (getAccountAuth() != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "register";
	}

	@PostMapping("/register")
	public String register1(Model model, @ModelAttribute("user") Account account) {
		if (account.getUsername().split(" ").length >= 2) {
			return "redirect:/register";
		} else {
			account.setRole(AccountRoleEnum.USER);
			account.setPassword(PasswordUtil.encode(account.getPassword()));
			account.setPhoto("noImage.jpg");
			aDAO.save(account);
			return "redirect:/login";
		}
	}

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/login/")
	public String loginValidation(@RequestParam("error") Boolean error, Model model) {
		if (error == true) {
			String message = "Sai tên đăng nhập hoặc mật khẩu!";
			model.addAttribute("message", message);
			return "login";
		}
		return "login";
	}

	@GetMapping("/profile")
	public String profile(Model model) {
		// Account a = getAccountAuth();
		// model.addAttribute("user", a);
		return "redirect:/order-history";
	}

	@GetMapping("/profile/{username}")
	public String profileUser(@PathVariable("username") String username, Model model) {
		Account a = aDAO.getById(username);

		model.addAttribute("user", a);
		return "profile";
	}

	@GetMapping("/profile/edit")
	public String editProfile(Model model) {
		return "profile-edit";
	}

	private final static String UPLOAD_DIR = "src\\main\\resources\\static\\images\\accountPhoto";

	@PostMapping("/profile/edit")
	public String editProfile(@RequestParam("file") MultipartFile file, Model model) {
		try {
			Account account = getAccountAuth();
			if (!file.isEmpty()) {
				Path uploadDir = Paths.get(UPLOAD_DIR);
				Path filePath = uploadDir.resolve(file.getOriginalFilename());
				Files.createDirectories(uploadDir);
				Files.write(filePath, file.getBytes());
				account.setPhoto(file.getOriginalFilename());
			}
			aDAO.save(account);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/profile";
	}

	@GetMapping("/order-history")
	public String orderhistory(Model model) {
		Account a = getAccountAuth();

		model.addAttribute("user", a);
		return "order-history";
	}

	public Account getAccountAuth() {
		return accountService.getAccountAuth();
	}

	@GetMapping("/oauth2/login/form")
	public String form() {
		return "oauth2/login";
	}

	@GetMapping("/oauth2/login/success")
	public String success(OAuth2AuthenticationToken oauth, Model model) throws IOException {
		String email = oauth.getPrincipal().getAttribute("email");
		String username = "GOOGLE_" + email.substring(0, email.indexOf("@"));
		String fullName = oauth.getPrincipal().getAttribute("name");
		String address = oauth.getPrincipal().getAttribute("address");
		String addressDetail = oauth.getPrincipal().getAttribute("address_detail");
		String photoUrl = oauth.getPrincipal().getAttribute("picture");

		String UPLOAD_DIR = "src\\main\\resources\\static\\images\\accountPhoto";

		Account account = aDAO.findById(username).orElse(null);

		if (account == null) {
			account = new Account();
			account.setUsername(username);
			account.setPassword(PasswordUtil.encode(RandomStringUtil.generateRandomString(20)));
			account.setEmail(email);

			if (address != null && !address.isEmpty() && !address.isBlank()) {
				account.setAddress(address);
			} else {
				account.setAddress("");
			}

			if (addressDetail != null && !addressDetail.isEmpty() && !addressDetail.isBlank()) {
				account.setAddressDetail(addressDetail);
			} else {
				account.setAddressDetail("");
			}
			account.setFullName(fullName);
			account.setRole(AccountRoleEnum.USER);

			if (photoUrl == null) {
				account.setPhoto("noImage.jpg");
			} else {
				String photoName = RandomStringUtil.generateRandomString(20) + ".png";

				try {
					URL url = new URL(photoUrl);
					Path uploadDir = Paths.get(UPLOAD_DIR);
					Path filePath = uploadDir.resolve(photoName);
					Files.createDirectories(uploadDir);

					try (InputStream in = url.openStream()) {
						Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
					}

					account.setPhoto(photoName);
				} catch (IOException e) {
					e.printStackTrace();
					// Xử lý lỗi tải hình ảnh
					return "redirect:/error";
				}
			}

			aDAO.save(account);
		} else {
			System.out.println("Account already exists");
		}

		AccountToUserDetail user = new AccountToUserDetail(account);
		Authentication auth = new UsernamePasswordAuthenticationToken(user, true, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		switch (getAccountAuth().getRole().name()) {
			case "USER":
			case "MANAGER": {
				return "redirect:/home";
			}
			case "ADMIN":
			case "SUPER_ADMIN": {
				return "redirect:/admin";
			}
			default:
				return "redirect:/home";
		}
	}

	@GetMapping("/oauth2/login/error")
	public String error() {
		return "redirect:/oauth2/authorization/google";
	}
}
