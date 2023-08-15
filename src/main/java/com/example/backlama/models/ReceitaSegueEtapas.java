package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "receita_segue_etapas")
public class ReceitaSegueEtapas {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @Id
    @Column(name = "id_etapas")
    private Long idEtapas;

    @Id
    @Column(name = "id_passos")
    private Long idPassos;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_etapas", referencedColumnName = "id_etapas", insertable = false, updatable = false),
            @JoinColumn(name = "id_passos", referencedColumnName = "id_passos", insertable = false, updatable = false)
    })
    private Etapas etapas;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    public ReceitaSegueEtapas() {}

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Long getIdEtapas() {
        return idEtapas;
    }

    public void setIdEtapas(Long idEtapas) {
        this.idEtapas = idEtapas;
    }

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
