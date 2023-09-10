package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_pessoal", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_receita", "id_usuario"})
})
public class ListaPessoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista_pessoal")
    private Integer id_lista_pessoal;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private User usuario;

    @Column(name = "progresso", length = 255, nullable = false)
    private String progresso;

    public ListaPessoal() {}

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Long getIdReceita(Receita receita){return receita.getIdReceita();}

    public void setIdReceita(Long id_receita){this.receita.setIdReceita(id_receita);}

    public Long getIdUser(User usuario){return usuario.getId();}

    public void serIdUser(Long id_usuario){this.usuario.setId(id_usuario);}

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getProgresso() {
        return progresso;
    }

    public void setProgresso(String progresso) {
        this.progresso = progresso;
    }

    public Integer getId_lista_pessoal() {
        return id_lista_pessoal;
    }
}
