package com.example.backlama.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@IdClass(EtapasId.class)
@Table(name = "etapas")
public class Etapas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etapas")
    private Long id_etapas;

    @Id
    @ManyToOne
    @JsonProperty("id_passos")
    @JoinColumn(name = "id_passos", referencedColumnName = "id_passos")
    private Passos id_passos;

    @JsonProperty("descricao")
    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    public Etapas() {}

    public Long getIdEtapas() {
        return id_etapas;
    }

    public void setIdEtapas(Long id_etapas) {
        this.id_etapas = id_etapas;
    }

    public Passos getPassos() {
        return id_passos;
    }

    public void setPassos(Passos id_passos) {
        this.id_passos = id_passos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdPassos() {
        return id_passos != null ? id_passos.getIdPassos() : null;
    }

    public void setIdPassos(Long idPassos) {
        if (id_passos == null) {
            id_passos = new Passos();
        }
        id_passos.setIdPassos(idPassos);
    }
}