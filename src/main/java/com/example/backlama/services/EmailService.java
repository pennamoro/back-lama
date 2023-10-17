package com.example.backlama.services;

import com.example.backlama.models.Usuario;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(Usuario usuario) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuario.getEmail());
        message.setSubject("Confirmação de Registro");
        message.setText("Olá " + usuario.getNome() + ",\n\n" +
                "Por favor, clique no link abaixo para confirmar seu registro:\n" +
                "http://localhost:8080/user/confirm?email=" + usuario.getEmail());

        mailSender.send(message);
    }
    public void sendDeleteEmail(Usuario usuario) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuario.getEmail());
        message.setSubject("Deleção de Conta na Lama");
        message.setText("Prezado(a) " + usuario.getNome() + ",\n\n" +
                "Lamentamos informar que sua conta na Lama foi deletada. Se você acredita que isso foi um erro ou deseja mais informações, entre em contato com nossa equipe de suporte.\n\n" +
                "Agradecemos por ter sido parte de nossa comunidade.\n\n" +
                "Atenciosamente,\n" +
                "Equipe de Suporte da Lama");

        mailSender.send(message);
    }
}