package com.example.backlama.services;

import com.example.backlama.models.ReceitaUtilizaMaterial;
import com.example.backlama.repositories.ReceitaUtilizaMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReceitaUtilizaMaterialService {
    public final ReceitaUtilizaMaterialRepository receitaUtilizaMaterialRepository;

    public ReceitaUtilizaMaterialService(ReceitaUtilizaMaterialRepository receitaUtilizaMaterialRepository) {
        this.receitaUtilizaMaterialRepository = receitaUtilizaMaterialRepository;
    }
    public void criarReceitaUtilizaMaterial(ReceitaUtilizaMaterial receitaUtilizaMaterial) {
        receitaUtilizaMaterialRepository.save(receitaUtilizaMaterial);
    }

    public ReceitaUtilizaMaterial buscarReceitaUtilizaMaterialPorId(Long id) {
        return receitaUtilizaMaterialRepository.findById(id).orElse(null);
    }
    public List<ReceitaUtilizaMaterial> buscarReceitaUtilizaMaterialPorIdReceita(Long idReceita) {
        return receitaUtilizaMaterialRepository.findByReceita_IdReceita(idReceita);
    }
    public ReceitaUtilizaMaterial editarReceitaUtilizaMaterial(Long id, ReceitaUtilizaMaterial novaRUM) {
        if (receitaUtilizaMaterialRepository.existsById(id)) {
            novaRUM.setId_receita_utiliza_material(id);
            return receitaUtilizaMaterialRepository.save(novaRUM);
        }
        return null;
    }
}

