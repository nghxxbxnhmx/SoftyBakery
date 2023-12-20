package com.poly.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.util.StandardCharset;
import com.poly.dao.AccountDAO;
import com.poly.dao.CategoryDAO;
import com.poly.dao.ProductDAO;
import com.poly.dto.ProductDTO;
import com.poly.models.Account;
import com.poly.models.MailInfo;
import com.poly.models.Order;
import com.poly.models.OrderItem;
import com.poly.services.AccountService;
import com.poly.services.CartService;
import com.poly.services.MailerService;
import com.poly.utils.CurrencyUtil;
import com.poly.utils.PasswordUtil;

@Controller
public class SendMailController {
    @Autowired
    MailerService mailerService;
    @Autowired
    CartService cart;

    @Autowired
    ProductDAO pDAO;
    @Autowired
    AccountDAO aDAO;
    @Autowired
    CategoryDAO cDAO;
    @Autowired
    AccountService accountService;

    // Contact
    @PostMapping("/contactMail")
    public String contactSendMail(Model model, @RequestParam String email, @RequestParam String name) {
        MailInfo mail = new MailInfo();
        mail.setTo(email);
        mail.setSubject("Cảm ơn bạn đã liên hệ với Softy Bakery");
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("<h4>Xin chào " + name + ", </h4>");
        bodyBuilder.append(
                "<p> >Chúng tôi xin chân thành cảm ơn bạn đã liên hệ với Softy Bakery . Chúng tôi rất vui mừng được hỗ trợ bạn và trả lời mọi câu hỏi của bạn</p>");
        bodyBuilder.append(
                "<p>Chúng tôi đã nhận được thông điệp của bạn và sẽ xem xét nhanh chóng. Đội ngũ chăm sóc khách hàng của chúng tôi sẽ cố gắng trả lời bạn trong thời gian sớm nhất có thể.</p>");
        bodyBuilder.append(
                "<p>Nếu bạn có bất kỳ yêu cầu hoặc thắc mắc cụ thể, đừng ngần ngại liên hệ trực tiếp với chúng tôi qua địa chỉ email [bakerysofty@gmail.com] hoặc số điện thoại [(+84) 869945854]</p>");
        bodyBuilder.append(
                "Chúng tôi mong muốn được phục vụ bạn và hy vọng rằng chúng tôi có thể đáp ứng đúng mong đợi của bạn.");
        mail.setBody(bodyBuilder.toString());
        // mailerService.queue(mail);

        System.out.println(email);
        return "redirect:/home";
    }

    // forgotPassWord
    @RestController
    public class PasswordResetController {
        @PostMapping("/forgotPassword")
        public ResponseEntity<String> sendPassword(@RequestParam String username) throws IOException {
            Account account = aDAO.getByUsername(username);

            if (account != null) {

                String resetCode = ResetCodeGenerator.generateResetCode();
                // account.setResetCode(resetCode);
                aDAO.save(account);
                MailInfo mail = new MailInfo();
                mail.setTo(account.getEmail());
                mail.setSubject("Your Password Recovery");
                StringBuilder bodyBuilder = new StringBuilder();
                bodyBuilder.append(new String(Files.readAllBytes(Paths.get("src\\main\\resources\\static\\data\\MailCss.txt")), StandardCharset.UTF_8));
                bodyBuilder.append("<p>Chào bạn,</p>");
                bodyBuilder.append("<p>Bạn (hoặc ai đó) đã yêu cầu khôi phục mật khẩu của bạn tại Softy Bakery.</p>");
                bodyBuilder.append("<p>Nếu đó là bạn, hãy sử dụng mã sau để khôi phục mật khẩu:</p>");
                bodyBuilder.append("<h4>").append(resetCode).append("</h4>");
                bodyBuilder.append("<p>Để đảm bảo an toàn, hãy thay đổi mật khẩu ngay sau khi đăng nhập.</p>");
                bodyBuilder.append("<p>Nếu bạn không yêu cầu khôi phục mật khẩu, hãy bỏ qua email này.</p>");
                mail.setBody(bodyBuilder.toString());
                mailerService.queue(mail);
                
                return ResponseEntity.ok().build();
            } else {
                String errorMessage = "Không tìm thấy tài khoản với tên đăng nhập đã nhập.";
                return ResponseEntity.status(400).body(errorMessage);
            }
        }

        private static class ResetCodeGenerator {

            public static String generateResetCode() {
                int numberOfDigits = 6;
                String digits = "0123456789";
                Random random = new Random();
                StringBuilder resetCodeBuilder = new StringBuilder(numberOfDigits);
                for (int i = 0; i < numberOfDigits; i++) {
                    int randomIndex = random.nextInt(digits.length());
                    resetCodeBuilder.append(digits.charAt(randomIndex));
                }
                return resetCodeBuilder.toString();
            }
        }
    }
    // @Controller
    // public class ResetPasswordController {

    // @Autowired
    // private AccountDAO aDAO;

    // @PostMapping("/resetPassword")
    // public ResponseEntity<Map<String, String>> resetPassword(
    // @RequestParam String username,
    // @RequestParam String resetCode,
    // @RequestParam String newPassword) {
    // Account account = aDAO.getByUsername(username);
    // Map<String, String> response = new HashMap<>();
    // if (account != null && resetCode.equals(account.getResetCode())) {
    // // Reset the password and clear the reset code
    // account.setPassword(PasswordUtil.encode(newPassword));
    // account.setResetCode(null); // Clear the reset code after using it
    // aDAO.save(account);
    // response.put("status", "success");
    // response.put("message", "Đổi mật khẩu thành công");
    // return ResponseEntity.ok(response);
    // } else {
    // response.put("status", "error");
    // response.put("message", "Thông tin đặt lại mật khẩu không hợp lệ.");
    // return ResponseEntity.ok(response);
    // }

    // OrderMail
    @PostMapping("/OrderMail")
    public String OderMaiil(Model model, @RequestBody Order order) throws IOException {
        MailInfo mail = new MailInfo();
        mail.setTo(order.getAccount().getEmail());
        mail.setSubject("Đơn hàng của bạn đã đặt thành công");

        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append(new String(Files.readAllBytes(Paths.get("src\\main\\resources\\static\\data\\MailCss.txt")), StandardCharset.UTF_8));
        bodyBuilder.append("Cảm ơn ").append(order.getAccount().getFullName())
                .append(" đã đặt hàng tại Softy Bakery<br><br>")
                .append("Đơn hàng sẽ được giao đến trong vòng 1 - 3 giờ tới, vui lòng chú ý điện thoại và theo dõi tình trạng đơn hàng của bạn.<br><br>")
                .append("Địa chỉ nhận hàng: ")
                .append(order.getAccount().getAddressDetail() + " ," + order.getAccount().getAddress())
                .append("<br><br>");

        bodyBuilder.append("<table class=\"styled-table\">");
        bodyBuilder.append(
                "<thead><tr><th scope=\"col\">Sản phẩm</th><th scope=\"col\">Số lượng</th><th scope=\"col\">Giá</th><th scope=\"col\">Tổng cộng</th></tr></thead>");
        bodyBuilder.append("<tbody>");
        for (OrderItem item : order.getOrderItems()) {
            bodyBuilder.append("<tr>");
            bodyBuilder.append("<td>").append(item.getProduct().getProductName()).append("</td>");
            bodyBuilder.append("<td>").append(item.getQuantity()).append("</td>");
            bodyBuilder.append("<td>").append(CurrencyUtil.formatVnd(item.getProduct().getPrice())).append("đ </td>");
            bodyBuilder.append("<td>").append("").append("đ </td>");
            bodyBuilder.append("</tr>");
        }
        bodyBuilder.append("</tbody>");

        bodyBuilder.append("<tfoot><tr><th colspan=\"2\">Tổng hóa đơn</th></tr></tfoot>");
        bodyBuilder.append("</table>");

        mail.setBody(bodyBuilder.toString());
        // mailerService.queue(mail);
        System.out.println(bodyBuilder.toString());
        return "redirect:/home";
    }
}