package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "material", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_tipo"})
})
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Long id_material;

    @ManyToOne
    @JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    private Tipo tipo;

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @Column(name = "icone", length = 255, nullable = false)
    private String icone;

    public Material() {}

    public Long getId_material() {
        return id_material;
    }

    public void setId_material(Long id_material) {
        this.id_material = id_material;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Long getIdTipo(){return tipo.getIdTipo();}

    public void setIdTipo(Long id_tipo){this.tipo.setIdTipo(id_tipo);}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
