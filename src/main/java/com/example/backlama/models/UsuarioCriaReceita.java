package com.example.backlama.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_cria_receita", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_receita", "id_usuario"})
})
public class UsuarioCriaReceita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_cria_receita")
    private Long id_usuario_cria_receita;

    @ManyToOne
    @JoinColumn(name = "id_receita", referencedColumnName = "id_receita")
    private Receita receita;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private User usuario;

    public UsuarioCriaReceita() {}

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public Long getIdReceita(Receita receita){return receita.getIdReceita();}

    public void setIdReceita(Long id_receita){this.receita.setIdReceita(id_receita);}

    public Long getIdUser(User usuario){return usuario.getId();}

    public void serIdUser(Long id_usuario){this.usuario.setId(id_usuario);}
}
