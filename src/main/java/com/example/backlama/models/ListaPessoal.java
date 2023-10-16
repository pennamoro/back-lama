package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "lista_pessoal")
public class ListaPessoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lista_pessoal")
    private Long idListaPessoal;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita", nullable = false)
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "progresso", length = 255, nullable = false)
    private String progresso;

    public ListaPessoal() {}

    public Long getIdListaPessoal() {
        return idListaPessoal;
    }

    public void setIdListaPessoal(Long idListaPessoal) {
        this.idListaPessoal = idListaPessoal;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Usuario getUser() {
        return usuario;
    }

    public void setUser(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getProgresso() {
        return progresso;
    }

    public void setProgresso(String progresso) {
        this.progresso = progresso;
    }
}
