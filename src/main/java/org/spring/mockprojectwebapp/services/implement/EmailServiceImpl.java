package org.spring.mockprojectwebapp.services.implement;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "[Tasty And Easy] Xác nhận địa chỉ email của bạn";
        String confirmationUrl = "http://localhost:8080/verify-email?token=" + token;

        // Nội dung email HTML
        String message = String.format(
                "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                        + "<h2 style='color: #2E86C1;'>Xác nhận địa chỉ email của bạn</h2>"
                        + "<p>Chào <strong>%s</strong>,</p>"
                        + "<p>Cảm ơn bạn đã đăng ký tài khoản tại <strong>Tasty And Easy</strong>! "
                        + "Để hoàn tất quá trình đăng ký, vui lòng xác nhận địa chỉ email của bạn bằng cách nhấn vào nút bên dưới:</p>"
                        + "<p style='text-align: center; margin: 20px 0;'>"
                        + "<a href='%s' style='background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Xác nhận Email</a>"
                        + "</p>"
                        + "<p>Nếu bạn không yêu cầu đăng ký, vui lòng bỏ qua email này.</p>"
                        + "<hr>"
                        + "<p style='color: #777;'>Trân trọng,<br><strong>Đội ngũ hỗ trợ Tasty And Easy</strong><br>"
                        + "Liên hệ: <a href='mailto:tnvan2903@gmail.com'>tnvan2903@gmail.com</a></p>"
                        + "</div>",
                user.getUsername(), confirmationUrl
        );

        try {
            MimeMessage email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email, true, "UTF-8");
            helper.setTo(recipientAddress);
            helper.setSubject(subject);
            helper.setText(message, true); // true để gửi email với nội dung HTML
            mailSender.send(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPasswordResetEmail(User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "[Tasty And Easy] Đặt lại mật khẩu của bạn";
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

        String message = String.format(
                "<div style='font-family: Arial, sans-serif; padding: 20px;'>"
                        + "<h2 style='color: #2E86C1;'>Đặt lại mật khẩu của bạn</h2>"
                        + "<p>Chào <strong>%s</strong>,</p>"
                        + "<p>Để đặt lại mật khẩu của bạn, vui lòng nhấn vào nút bên dưới:</p>"
                        + "<p style='text-align: center; margin: 20px 0;'>"
                        + "<a href='%s' style='background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Đặt lại mật khẩu</a>"
                        + "</p>"
                        + "<p>Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.</p>"
                        + "<hr>"
                        + "<p style='color: #777;'>Trân trọng,<br><strong>Đội ngũ hỗ trợ Tasty And Easy</strong><br>"
                        + "Liên hệ: <a href='mailto:tnvan2903@gmail.com'>tnvan2903@gmail.com</a></p>"
                        + "</div>",
                user.getUsername(), resetUrl
        );

        try {
            MimeMessage email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email, true, "UTF-8");
            helper.setTo(recipientAddress);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
