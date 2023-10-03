package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "receita_utiliza_material", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_receita", "id_material", "id_tipo"})
})
public class ReceitaUtilizaMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_receita_utiliza_material")
    private Long id_receita_utiliza_material;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_material", referencedColumnName = "id_material"),
            @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    })
    private Material material;
    public ReceitaUtilizaMaterial() {}

    public Long getId_receita_utiliza_material() {
        return id_receita_utiliza_material;
    }

    public void setId_receita_utiliza_material(Long id_receita_utiliza_material) {
        this.id_receita_utiliza_material = id_receita_utiliza_material;
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