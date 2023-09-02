package com.example.backlama.models;

import java.io.Serializable;
import java.util.Objects;

public class ReceitaSegueEtapasId implements Serializable {
    private Receita id_receita;
    private Etapas id_etapas;

    public ReceitaSegueEtapasId() {
    }

    public ReceitaSegueEtapasId(Receita  id_receita, Etapas id_etapas){
        this.id_receita = id_receita;
        this.id_etapas = id_etapas;
    }

    public Receita getId_receita() {
        return id_receita;
    }

    public void setId_receita(Receita id_receita) {
        this.id_receita = id_receita;
    }

    public Etapas getId_etapas() {
        return id_etapas;
    }

    public void setId_etapas(Etapas id_etapas) {
        this.id_etapas = id_etapas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceitaSegueEtapasId that = (ReceitaSegueEtapasId) o;
        return Objects.equals(id_receita, that.id_receita) &&
                Objects.equals(id_etapas, that.id_etapas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_receita, id_etapas);
    }
}
