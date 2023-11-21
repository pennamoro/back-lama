package com.example.backlama.dto;

import com.example.backlama.models.*;

import java.util.List;

public class ReceitaAvaliacaoDTO {
    private Receita receita;
    private List<ReceitaUtilizaMaterial> receitaUtilizaMaterial;
    private List<ReceitaSeparadaCategoria> receitaSeparadaCategoria;
    private List<EtapasDTO> Etapas;
    private List<AvaliacaoDTO> Avaliacoes;

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public List<ReceitaUtilizaMaterial> getReceitaUtilizaMaterial() {
        return receitaUtilizaMaterial;
    }

    public void setReceitaUtilizaMaterial(List<ReceitaUtilizaMaterial> receitaUtilizaMaterial) {
        this.receitaUtilizaMaterial = receitaUtilizaMaterial;
    }

    public List<ReceitaSeparadaCategoria> getReceitaSeparadaCategoria() {
        return receitaSeparadaCategoria;
    }

    public void setReceitaSeparadaCategoria(List<ReceitaSeparadaCategoria> receitaSeparadaCategoria) {
        this.receitaSeparadaCategoria = receitaSeparadaCategoria;
    }

    public List<EtapasDTO> getEtapas() {
        return Etapas;
    }

    public void setEtapas(List<EtapasDTO> etapas) {
        Etapas = etapas;
    }

    public List<AvaliacaoDTO> getAvaliacoes() {
        return Avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoDTO> avaliacoes) {
        Avaliacoes = avaliacoes;
    }
}
