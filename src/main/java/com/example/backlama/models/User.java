package com.example.backlama.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "pessoa")
public class User {

    @Id
    @Column(name="id_pessoa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pessoa;

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
    private String nivel_experiencia;

    public User() {}

    public Long getId() { return id_pessoa; }

    public void setId(Long id_pessoa) { this.id_pessoa = id_pessoa; }

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
        return nivel_experiencia;
    }

    public void setNivelExperiencia(String nivel_experiencia) {
        this.nivel_experiencia = nivel_experiencia;
    }
}