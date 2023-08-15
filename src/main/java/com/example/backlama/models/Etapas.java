package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "etapas")
public class Etapas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etapas")
    private Long idEtapas;

    @ManyToOne
    @JoinColumn(name = "id_passos", referencedColumnName = "id_passos")
    private Passos passos;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    public Etapas() {}

    public Long getIdEtapas() {
        return idEtapas;
    }

    public void setIdEtapas(Long idEtapas) {
        this.idEtapas = idEtapas;
    }

    public Passos getPassos() {
        return passos;
    }

    public void setPassos(Passos passos) {
        this.passos = passos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
