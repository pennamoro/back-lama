package com.example.backlama.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name="id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(name="nome")
    @JsonProperty("nome")
    private String nome;

    @Column(name="apelido")
    @JsonProperty("apelido")
    private String apelido;

    @Column(name="email")
    @JsonProperty("email")
    private String email;

    @Column(name="senha")
    @JsonProperty("senha")
    private String senha;

    @Column(name="nivel_experiencia")
    @JsonProperty("nivel_experiencia")
    private String nivelExperiencia;
    @Column(name = "confirmed")
    @JsonProperty("confirmed")
    private boolean confirmed;
    @Column(name = "is_admin")
    @JsonProperty("admin")
    private boolean isAdmin;

    @Column(name ="foto")
    @JsonProperty("foto")
    private String foto;

    public Usuario() {}

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivelExperiencia() {
        return nivelExperiencia;
    }

    public void setNivelExperiencia(String nivelExperiencia) {
        this.nivelExperiencia = nivelExperiencia;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
}