package com.example.backlama.controllers;

import com.example.backlama.models.Usuario;
import com.example.backlama.services.UsuarioService;
import com.example.backlama.services.EmailService;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@ComponentScan(basePackageClasses = UsuarioService.class)
@RequestMapping("/user")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final EmailService emailService;

    public UsuarioController(UsuarioService usuarioService, EmailService emailService) {
        this.usuarioService = usuarioService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
        Usuario registeredUsuario = usuarioService.registerUsuario(usuario);
        emailService.sendConfirmationEmail(registeredUsuario);
        return new ResponseEntity<>(registeredUsuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<Map<String, Object>> loginUsuario(@RequestBody Usuario usuario) {
        Map<String, Object> response = usuarioService.loginUsuario(usuario.getEmail(), usuario.getSenha());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/confirm")
    @PermitAll
    public ResponseEntity<String> confirmRegistration(@RequestParam("email") String email) {
        boolean confirmed = usuarioService.confirmRegistration(email);
        if (confirmed) {
            return new ResponseEntity<>("Registro confirmado com sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Falha ao confirmar o registro. Token inválido ou expirado.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> visualizarPessoa(@PathVariable Long id){
        Usuario usuario = usuarioService.buscarUsuarioById(id);
        if (usuario != null) {
            usuario.setSenha(null);
            usuario.setAdmin(false);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario updatedUsuario) {
        Usuario existingUsuario = usuarioService.buscarUsuarioById(id);
        if (existingUsuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingUsuario.setNome(updatedUsuario.getNome());
        existingUsuario.setApelido(updatedUsuario.getApelido());
        existingUsuario.setEmail(updatedUsuario.getEmail());
        existingUsuario.setSenha(updatedUsuario.getSenha());
        existingUsuario.setNivelExperiencia(updatedUsuario.getNivelExperiencia());

        Usuario updated = usuarioService.updateUsuario(existingUsuario);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}