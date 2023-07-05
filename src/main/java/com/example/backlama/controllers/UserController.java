package com.example.backlama.controllers;

import com.example.backlama.models.User;
import com.example.backlama.services.UserService;
import com.example.backlama.services.EmailService;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@ComponentScan(basePackageClasses = UserService.class)
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    public UserController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        emailService.sendConfirmationEmail(registeredUser);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        String token = userService.loginUser(user.getEmail(), user.getSenha());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/confirm")
    @PermitAll
    public ResponseEntity<String> confirmRegistration(@RequestParam("email") String email) {
        boolean confirmed = userService.confirmRegistration(email);
        if (confirmed) {
            return new ResponseEntity<>("Registro confirmado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao confirmar o registro. Token inválido ou expirado.", HttpStatus.BAD_REQUEST);
        }
    }
}





