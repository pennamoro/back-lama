package com.example.backlama.dto;

import com.example.backlama.models.Receita;

import java.util.List;

public class ReceitaCriarDTO{
        private Receita receita;
        private List<Long> receitaUtilizaMaterialIds;
        private List<Long> receitaSeparadaCategoriaIds;
        private List<EtapasDTO> receitaSegueEtapas;

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

    public List<Long> getReceitaSeparadaCategoriaIds() {
        return receitaSeparadaCategoriaIds;
    }

    public void setReceitaSeparadaCategoriaIds(List<Long> receitaSeparadaCategoriaIds) {
        this.receitaSeparadaCategoriaIds = receitaSeparadaCategoriaIds;
    }

    public List<EtapasDTO> getReceitaSegueEtapas() {
        return receitaSegueEtapas;
    }

    public void setReceitaSegueEtapas(List<EtapasDTO> receitaSegueEtapas) {
        this.receitaSegueEtapas = receitaSegueEtapas;
    }
}
