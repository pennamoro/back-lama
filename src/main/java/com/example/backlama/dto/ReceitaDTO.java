package com.example.backlama.dto;

import com.example.backlama.models.*;

import java.util.List;

public class ReceitaDTO {
        private Receita receita;
        private List<ReceitaUtilizaMaterial> receitaUtilizaMaterial;
        private List<ReceitaSeparadaCategoria> receitaSeparadaCategoria;
        private List<ReceitaSegueEtapas> receitaSegueEtapas;

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

    public List<ReceitaSegueEtapas> getReceitaSegueEtapas() {
        return receitaSegueEtapas;
    }

    public void setReceitaSegueEtapas(List<ReceitaSegueEtapas> receitaSegueEtapas) {
        this.receitaSegueEtapas = receitaSegueEtapas;
    }
}
