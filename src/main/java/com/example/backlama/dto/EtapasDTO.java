package com.example.backlama.dto;

import com.example.backlama.models.Passos;

import java.util.List;

public class EtapasDTO {
    private List<Passos> passos;
    private String descricao_etapas;

    public List<Passos> getPassosList() {
        return passos;
    }

    public void setPassosList(List<Passos> passosList) {
        this.passos = passosList;
    }

    public String getDescricao_etapas() {
        return descricao_etapas;
    }

    public void setDescricao_etapas(String descricao_etapas) {
        this.descricao_etapas = descricao_etapas;
    }

    public String getDescricao() {
        return descricao_etapas;
    }

    public void setDescricao(String descricao) {
        this.descricao_etapas = descricao;
    }
}