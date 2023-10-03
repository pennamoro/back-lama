package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "receita_segue_etapas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_receita", "id_etapas", "id_passos"})
})
public class ReceitaSegueEtapas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_receita_segue_etapas")
    private Long id_receita_segue_etapas;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_etapas", referencedColumnName = "id_etapas"),
            @JoinColumn(name = "id_passos", referencedColumnName = "id_passos")
    })
    private Etapas etapas;
    public ReceitaSegueEtapas(){}

    public Long getId_receita_segue_etapas() {
        return id_receita_segue_etapas;
    }

    public void setId_receita_segue_etapas(Long id_receita_segue_etapas) {
        this.id_receita_segue_etapas = id_receita_segue_etapas;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Etapas getEtapas() {
        return etapas;
    }

    public void setEtapas(Etapas etapas) {
        this.etapas = etapas;
    }
}