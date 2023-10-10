package com.example.backlama.services;

import com.example.backlama.security.SecurityConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.backlama.models.User;
import com.example.backlama.repositories.UserRepository ;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackageClasses = UserRepository.class)
@ComponentScan(basePackageClasses = SecurityConfig.class)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        // Verificar se o email já está sendo usado
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email já está em uso");
        }

        // Criptografar a senha antes de salvar
        user.setSenha(passwordEncoder.encode(user.getSenha()));

        user.setConfirmed(false);
        return userRepository.save(user);
    }

    public Map<String, Object> loginUser(String email, String senha) {
        User user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(senha, user.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }
        if (!user.isConfirmed()){
            throw new RuntimeException("Conta ainda não verificada");
        }
        String token  = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("isAdmin", user.isAdmin());
        response.put("userId", user.getId());
        System.out.println(user.isAdmin());
        return response;
    }
    public boolean confirmRegistration(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null && !user.isConfirmed()) {
            user.setConfirmed(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User buscarUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        existingUser.setNome(updatedUser.getNome());
        existingUser.setApelido(updatedUser.getApelido());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setSenha(updatedUser.getSenha());
        existingUser.setNivelExperiencia(updatedUser.getNivelExperiencia());

        return userRepository.save(existingUser);
    }
}