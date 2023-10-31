package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "receita_utiliza_material")
public class ReceitaUtilizaMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receita_utiliza_material")
    private Long idReceitaUtilizaMaterial;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    private Material material;
    public ReceitaUtilizaMaterial() {}

    public Long getIdReceitaUtilizaMaterial() {
        return idReceitaUtilizaMaterial;
    }

    public void setIdReceitaUtilizaMaterial(Long idReceitaUtilizaMaterial) {
        this.idReceitaUtilizaMaterial = idReceitaUtilizaMaterial;
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}