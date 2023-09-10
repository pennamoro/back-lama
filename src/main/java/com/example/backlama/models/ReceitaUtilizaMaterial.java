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

    @Column(name = "id_material")
    private Long id_material;

    @Column(name = "id_tipo")
    private Long id_tipo;

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

    public Long getIdReceita(Receita recetia){return this.receita.getIdReceita();}

    public void setIdReceita(Long id_receita){this.receita.setIdReceita(id_receita);}

    public Long getIdMaterial() {
        return id_material;
    }

    public void setIdMaterial(Long idMaterial) {
        this.id_material = idMaterial;
    }

    public Long getIdTipo() {
        return id_tipo;
    }

    public void setIdTipo(Long id_tipo) {
        this.id_tipo = id_tipo;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Long getId_receita_utiliza_material() {
        return id_receita_utiliza_material;
    }
}
