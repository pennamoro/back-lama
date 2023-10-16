package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "passos")
public class Passos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passos")
    private Long idPassos;

    @ManyToOne
    @JoinColumn(name = "id_etapas", referencedColumnName = "id_etapas", nullable = false)
    private Etapas etapas;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    public Passos() {}

    public Long getIdPassos() {
        return idPassos;
    }

    public void setIdPassos(Long idPassos) {
        this.idPassos = idPassos;
    }

    public Etapas getEtapas() {
        return etapas;
    }

    public void setEtapas(Etapas etapas) {
        this.etapas = etapas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}