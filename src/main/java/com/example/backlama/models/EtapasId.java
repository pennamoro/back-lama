package com.example.backlama.models;

import java.io.Serializable;
import java.util.Objects;

public class EtapasId implements Serializable {
    private Long id_etapas;
    private Passos id_passos;

    public EtapasId() {
    }

    public EtapasId(Long id_etapas, Passos id_passos){
        this.id_etapas = id_etapas;
        this.id_passos = id_passos;
    }

    public Long getIdEtapas() {
        return id_etapas;
    }

    public void setIdEtapas(Long id_etapas) {
        this.id_etapas = id_etapas;
    }

    public Long getIdPassos() {
        return id_passos.getIdPassos();
    }

    public void setIdPassos(Long id_passos) {
        this.id_passos.setIdPassos(id_passos);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EtapasId etapasId = (EtapasId) o;
        return Objects.equals(id_etapas, etapasId.id_etapas) &&
                Objects.equals(id_passos, etapasId.id_passos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_etapas, id_passos);
    }
}
