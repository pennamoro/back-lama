package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "receita_utiliza_material")
public class ReceitaUtilizaMaterial {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @Id
    @Column(name = "id_material")
    private Long idMaterial;

    @Id
    @Column(name = "id_tipo")
    private Long idTipo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_material", referencedColumnName = "id_material", insertable = false, updatable = false),
            @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo", insertable = false, updatable = false)
    })
    private Material material;

    public ReceitaUtilizaMaterial() {}

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
