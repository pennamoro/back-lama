package com.example.backlama.services;

import com.example.backlama.models.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(User user) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Confirmação de Registro");
        message.setText("Olá " + user.getNome() + ",\n\n" +
                "Por favor, clique no link abaixo para confirmar seu registro:\n" +
                "http://localhost:8080/user/confirm?email=" + user.getEmail());

        mailSender.send(message);
    }

}


