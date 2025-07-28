package com.gamexd.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailValidationService {
    @Value("${app.base-url}")
    private String baseUrl;
    private final JavaMailSender mailSender;

    public EmailValidationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String destiny, String token) {
        String link = baseUrl + "/auth/validate?token=" + token;
        String htmlContent = """
                <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                        <div style="background-color: #ffffff; padding: 20px; border-radius: 8px;">
                            <h2 style="color: #333333;">Confirmação de Cadastro</h2>
                            <p style="color: #555555;">Clique no botão abaixo para confirmar seu cadastro:</p>
                            <a href="%s" style="display: inline-block; padding: 10px 20px; background-color: #007BFF; 
                                color: white; text-decoration: none; border-radius: 4px; margin-top: 10px;">
                                Confirmar Cadastro
                            </a>
                            <a href="%s">%s</a>
                            <p style="color: #999999; margin-top: 20px;">Se você não se cadastrou, ignore este e-mail.</p>
                        </div>
                    </body>
                </html>
                """.formatted(link, link, link);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(destiny);
            helper.setSubject("Confirme seu cadastro");
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de confirmação", e);
        }
    }
}

