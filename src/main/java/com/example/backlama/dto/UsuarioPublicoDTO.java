package com.example.backlama.dto;

import com.example.backlama.models.Receita;
import com.example.backlama.models.Usuario;

import java.util.List;

public class UsuarioPublicoDTO {
    private Usuario usuario;
    private List<Receita> receitasPublicas;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Receita> getReceitasPublicas() {
        return receitasPublicas;
    }

    public void setReceitasPublicas(List<Receita> receitasPublicas) {
        this.receitasPublicas = receitasPublicas;
    }
}
