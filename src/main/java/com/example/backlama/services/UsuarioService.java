package com.example.backlama.services;

import com.example.backlama.models.Usuario;
import com.example.backlama.security.SecurityConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.backlama.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackageClasses = UsuarioRepository.class)
@ComponentScan(basePackageClasses = SecurityConfig.class)
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registerUsuario(Usuario usuario) {
        // Verificar se o email já está sendo usado
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new RuntimeException("Email já está em uso");
        }

        // Criptografar a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        usuario.setConfirmed(false);
        return usuarioRepository.save(usuario);
    }

    public Map<String, Object> loginUsuario(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null || !passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }
        if (!usuario.isConfirmed()){
            throw new RuntimeException("Conta ainda não verificada");
        }
        String token  = Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("isAdmin", usuario.isAdmin());
        response.put("userId", usuario.getIdUsuario());
        System.out.println(usuario.isAdmin());
        return response;
    }
    public boolean confirmRegistration(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && !usuario.isConfirmed()) {
            usuario.setConfirmed(true);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public Usuario buscarUsuarioById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }
    public Usuario updateUsuario(Usuario updatedUsuario) {
        Usuario existingUsuario = usuarioRepository.findById(updatedUsuario.getIdUsuario())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        existingUsuario.setNome(updatedUsuario.getNome());
        existingUsuario.setApelido(updatedUsuario.getApelido());
        existingUsuario.setEmail(updatedUsuario.getEmail());
        existingUsuario.setSenha(passwordEncoder.encode(updatedUsuario.getSenha()));
        existingUsuario.setNivelExperiencia(updatedUsuario.getNivelExperiencia());

        return usuarioRepository.save(existingUsuario);
    }
    public void deleteUsuario(Usuario usuario){
        usuarioRepository.delete(usuario);
    }
    public List<Usuario> listarTodos(){return usuarioRepository.findAll();}
}