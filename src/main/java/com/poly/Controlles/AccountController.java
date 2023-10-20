package com.poly.Controlles;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.poly.dao.AccountDAO;
import com.poly.model.Account;
import com.poly.services.FileStorageService;

import DB.UserUtils;

@Controller
public class AccountController {
	@Autowired AccountDAO aDAO;
	@Autowired FileStorageService fileService;
		
	@GetMapping("/orderhistory")
	public String orderhistory(Model model){
		Account a = getAccountAuth();
		
		model.addAttribute("user", a);
		return "orderhistory";
	}
	@GetMapping("/register")
	public String register(Model model) {
		Account a = new Account();
		a.setAccountId(aDAO.getTopAccountId()+1);
		model.addAttribute("user",a);
		return "register";
	}
	@PostMapping("/register")
	public String register1(Model model, @ModelAttribute("user") Account a) {
		
		a.setAccountId(aDAO.getTopAccountId()+1);
		a.setAdmin(false);
		a.setPassword(passwordEncoder().encode(a.getPassword()));
		a.setPhoto("noImage.jpg");
		System.out.println(a.toStringDetail());
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
	@GetMapping("/profile/edit")
	public String editProfile(Model model) {
		return "profile-edit";
	}
	private static final String CLIENT_ID = "0ab0fb9877708c6";
    private static final String CLIENT_SECRET = "d55ae0d35f1f889cd6be96aca5684032c59ac50a";
    private static final String IMGUR_UPLOAD_URL = "https://api.imgur.com/3/upload";

    @PostMapping("/profile/edit")
    public String editProfile(@RequestParam("file") MultipartFile file, Model model) {
        // try {
        //     Account user = getAccountAuth();
        //     if (!file.isEmpty()) {
        //         CloseableHttpClient httpClient = HttpClients.createDefault();

        //         // Xác thực bằng OAuth2 và nhận access token
        //         String accessToken = authenticateAndGetAccessToken();

        //         if (accessToken != null) {
        //             HttpPost httpPost = new HttpPost(IMGUR_UPLOAD_URL);
        //             httpPost.setHeader("Authorization", "Bearer " + accessToken);

        //             // Sử dụng HttpMultipartMode.BROWSER_COMPATIBLE cho MultipartEntityBuilder
        //             MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        //             builder.addBinaryBody("image", file.getBytes(), ContentType.DEFAULT_BINARY, file.getOriginalFilename());
        //             HttpEntity entity = builder.build();

        //             httpPost.setEntity(entity);

        //             CloseableHttpResponse response = httpClient.execute(httpPost);

        //             // Xử lý phản hồi từ Imgur
        //             if (response.getStatusLine().getStatusCode() == 200) {
        //                 String responseBody = EntityUtils.toString(response.getEntity());
        //                 // Phân tích JSON phản hồi để lấy URL hình ảnh đã tải lên
        //                 // Thực hiện lưu URL vào cơ sở dữ liệu hoặc bất kỳ hành động nào khác bạn muốn.
        //                 // Ví dụ: user.setPhoto(imgurImageUrl);
        //             }
        //         }

        //         httpClient.close();
        //     }
        //     aDAO.save(user);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        return "redirect:/profile";
    }

    // Hàm để xác thực và nhận access token từ Imgur
    private String authenticateAndGetAccessToken() {
        // Thực hiện xác thực và nhận access token từ Imgur
        // Sử dụng CLIENT_ID và CLIENT_SECRET để thực hiện xác thực
        // Trả về access token hoặc null nếu xác thực không thành công
        // Đây là nơi bạn cần triển khai xác thực OAuth2 với Imgur API.
        return null;
    }	
	
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	public Account getAccountAuth() { 
		return aDAO.getByUserName(UserUtils.getUser().getUsername());
	}
}
