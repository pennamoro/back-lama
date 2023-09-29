package com.example.backlama.controllers;

import com.example.backlama.models.User;
import com.example.backlama.services.UserService;
import com.example.backlama.services.EmailService;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@ComponentScan(basePackageClasses = UserService.class)
@RequestMapping("/user")
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
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody User user) {
        Map<String, Object> response = userService.loginUser(user.getEmail(), user.getSenha());
        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @GetMapping("/{id}")
    public ResponseEntity<User> visualizarPessoa (@PathVariable Long id){
        User user = userService.buscarUserById(id);
        user.setSenha(null);
        user.setAdmin(false);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.buscarUserById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingUser.setNome(updatedUser.getNome());
        existingUser.setApelido(updatedUser.getApelido());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setSenha(updatedUser.getSenha());
        existingUser.setNivelExperiencia(updatedUser.getNivelExperiencia());

        User updated = userService.updateUser(existingUser);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

}