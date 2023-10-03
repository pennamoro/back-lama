package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "receita_separada_categoria", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_receita", "id_categoria"})
})
public class ReceitaSeparadaCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_receita_separada_categoria")
    private Long id_receita_separada_categoria;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    private Categoria categoria;

    public ReceitaSeparadaCategoria() {}

    public Long getId_receita_separada_categoria() {
        return id_receita_separada_categoria;
    }

    public void setId_receita_separada_categoria(Long id_receita_separada_categoria) {
        this.id_receita_separada_categoria = id_receita_separada_categoria;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}