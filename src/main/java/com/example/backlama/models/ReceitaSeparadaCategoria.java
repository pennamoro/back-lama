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
    private Long idReceitaSeparadaCategoria;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    private Categoria categoria;

    public ReceitaSeparadaCategoria() {}

    public Long getIdReceitaSeparadaCategoria() {
        return idReceitaSeparadaCategoria;
    }

    public void setIdReceitaSeparadaCategoria(Long idReceitaSeparadaCategoria) {
        this.idReceitaSeparadaCategoria = idReceitaSeparadaCategoria;
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