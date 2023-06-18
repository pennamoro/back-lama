package com.example.backlama.models;


import jakarta.persistence.*;

@Entity
@Table(name = "pessoa")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String apelido;

    private String email;

    private String senha;

    private String nivelExperiencia;

    public User(Long id, String nome, String apelido, String email, String senha, String nivelExperiencia) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.senha = senha;
        this.nivelExperiencia = nivelExperiencia;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
