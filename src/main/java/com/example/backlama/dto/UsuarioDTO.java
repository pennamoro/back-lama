package com.example.backlama.dto;

import com.example.backlama.models.*;

import java.util.List;

public class UsuarioDTO {
    private Usuario usuario;
    private List<Material> materiais;
    private List<Receita> listaPessoal;
    private List<Receita> receitaPessoal;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }

    public List<Receita> getListaPessoal() {
        return listaPessoal;
    }

    public void setListaPessoal(List<Receita> listaPessoal) {
        this.listaPessoal = listaPessoal;
    }

    public List<Receita> getReceitaPessoal() {
        return receitaPessoal;
    }

    public void setReceitaPessoal(List<Receita> receitaPessoal) {
        this.receitaPessoal = receitaPessoal;
    }
}