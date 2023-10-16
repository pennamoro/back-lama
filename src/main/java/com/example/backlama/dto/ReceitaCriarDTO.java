package com.example.backlama.dto;

import com.example.backlama.models.Receita;

import java.util.List;

public class ReceitaCriarDTO{
        private Receita receita;
        private Long userId;
        private List<Long> receitaUtilizaMaterialIds;
        private List<Long> receitaSeparadaCategoriaIds;
        private List<EtapasDTO> etapas;

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public List<Long> getReceitaUtilizaMaterialIds() {
        return receitaUtilizaMaterialIds;
    }

    public void setReceitaUtilizaMaterialIds(List<Long> receitaUtilizaMaterialIds) {
        this.receitaUtilizaMaterialIds = receitaUtilizaMaterialIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getReceitaSeparadaCategoriaIds() {
        return receitaSeparadaCategoriaIds;
    }

    public void setReceitaSeparadaCategoriaIds(List<Long> receitaSeparadaCategoriaIds) {
        this.receitaSeparadaCategoriaIds = receitaSeparadaCategoriaIds;
    }

    public List<EtapasDTO> getEtapas() {
        return etapas;
    }

    public void setReceitaSegueEtapas(List<EtapasDTO> etapas) {
        this.etapas = etapas;
    }
}