package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@IdClass(ReceitaSegueEtapasId.class)
@Table(name = "receita_segue_etapas")
public class ReceitaSegueEtapas {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita id_receita;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_etapas", referencedColumnName = "id_etapas", insertable = false, updatable = false),
            @JoinColumn(name = "id_passos", referencedColumnName = "id_passos", insertable = false, updatable = false)
    })
    private Etapas id_etapas;

    public ReceitaSegueEtapas() {}

    public Receita getReceita() {
        return id_receita;
    }

    public void setReceita(Receita receita) {
        this.id_receita = receita;
    }

    public Long getIdEtapas() {
        return id_etapas != null ? id_etapas.getIdEtapas() : null;
    }

    public void setIdEtapas(Long idEtapas) {
        if (id_etapas == null) {
            id_etapas = new Etapas();
        }
        id_etapas.setIdEtapas(idEtapas);
    }

    public Long getIdPassos() {
        return id_etapas != null ? id_etapas.getIdPassos() : null;
    }

    public void setIdPassos(Long idPassos) {
        if (id_etapas == null) {
            id_etapas = new Etapas();
        }
        id_etapas.setIdPassos(idPassos);
    }

    public Etapas getEtapas() { return id_etapas; }

    public void setEtapas(Etapas etapas) {
        this.id_etapas = etapas;
    }

    public Long getIdReceita(){
        return id_receita != null ? id_receita.getIdReceita() : null;
    }

    public void setIdReceita(Long idReceita){
        if (id_receita == null) {
            id_receita = new Receita();
        }
        id_receita.setIdReceita(idReceita);
    }
}
