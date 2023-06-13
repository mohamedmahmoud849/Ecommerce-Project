package com.vodafone.ecommerce.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.vodafone.ecommerce.model.Mail;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendVerifyMail(String email,Long id) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1 style=\"alignment:centre; text-align: center;\">Please Verify Your Email</h1>\n" +
                "<br>\n" +
                "<div style=\"margin-left:400px\">\n" +
                "<a href=\"http://localhost:8090/verify/"+id+"\">\n" +
                "<button type=\"button\" style=\"position: absolute;\n" +
                "    top: 30%;\n" +
                "    left: 50%;\n" +
                "    margin-top: -50px;\n" +
                "    margin-left: -50px;\n" +
                "    width: 150px;\n" +
                "    height: 70px;\n" +
                "    background-color: #4CAF50; /* Green */\n" +
                "  border: none;\n" +
                "  color: white;\n" +
                "  padding: 15px 32px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\n" +
                "  font-size: 16px;\n" +
                "  border-radius: 4px;\n" +
                "\" >Verify</button>\n" +
                "</a>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(email);
        helper.setSubject("verify Email");
        helper.setFrom("abc@gmail.com");
        javaMailSender.send(mimeMessage);

    }
    public void sendResetPasswordMail(String email,Long id) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1 style=\"alignment:centre; text-align: center;\">Please Verify Your Email</h1>\n" +
                "<br>\n" +
                "<div style=\"margin-left:400px\">\n" +
                "<a href=\"http://localhost:8090/reset/"+id+"\">\n" +
                "<button type=\"button\" style=\"position: absolute;\n" +
                "    top: 30%;\n" +
                "    left: 50%;\n" +
                "    margin-top: -50px;\n" +
                "    margin-left: -50px;\n" +
                "    width: 150px;\n" +
                "    height: 70px;\n" +
                "    background-color: #4CAF50; /* Green */\n" +
                "  border: none;\n" +
                "  color: white;\n" +
                "  padding: 15px 32px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\n" +
                "  font-size: 16px;\n" +
                "  border-radius: 4px;\n" +
                "\" >Reset Password</button>\n" +
                "</a>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(email);
        helper.setSubject("reset Password");
        helper.setFrom("abc@gmail.com");
        javaMailSender.send(mimeMessage);

    }
}
