package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "avaliacao", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_receita", "id_usuario"})
})
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avaliacao")
    private Long id_avaliacao;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private User usuario;

    @Column(name = "estrelas")
    private Integer estrelas;

    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "titulo", length = 255)
    private String titulo;

    public Avaliacao() {}

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Long getIdReceita(Receita receita){return receita.getIdReceita();}

    public void setIdReceita(Long id_receita){this.receita.setIdReceita(id_receita);}

    public Long getIdUser(User usuario){return usuario.getId();}

    public void serIdUser(Long id_usuario){this.usuario.setId(id_usuario);}

    public Integer getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(Integer estrelas) {
        this.estrelas = estrelas;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId_avaliacao() {
        return id_avaliacao;
    }
}
