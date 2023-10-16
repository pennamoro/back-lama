package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_possui_material", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_usuario", "id_material"})
})
public class UsuarioPossuiMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_possui_material")
    private Long idUsuarioPossuiMaterial;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_material", referencedColumnName = "id_material")
    private Material material;

    public UsuarioPossuiMaterial() {}

    public Long getIdUsuarioPossuiMaterial() {
        return idUsuarioPossuiMaterial;
    }

    public void setIdUsuarioPossuiMaterial(Long idUsuarioPossuiMaterial) {
        this.idUsuarioPossuiMaterial = idUsuarioPossuiMaterial;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}

