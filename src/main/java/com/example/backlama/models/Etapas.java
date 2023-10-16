package com.example.backlama.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "etapas")
public class Etapas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etapas")
    private Long idEtapas;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita", nullable = false)
    private Receita receita;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    @JsonProperty("descricao")
    private String descricao;

    public Etapas() {}

    public Long getIdEtapas() {
        return idEtapas;
    }

    public void setIdEtapas(Long idEtapas) {
        this.idEtapas = idEtapas;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}