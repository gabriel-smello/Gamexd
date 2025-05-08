package com.gamexd.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        var message = new SimpleMailMessage();
        message.setTo(destiny);
        message.setSubject("Confirme seu cadastro");
        message.setText("Clique no link para confirmar: " + baseUrl + "/auth/validate?token=" + token);
        mailSender.send(message);
    }
}
