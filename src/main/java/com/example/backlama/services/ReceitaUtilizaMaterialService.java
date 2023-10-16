package com.example.backlama.services;

import com.example.backlama.models.ReceitaUtilizaMaterial;
import com.example.backlama.repositories.ReceitaUtilizaMaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
            novaRUM.setIdReceitaUtilizaMaterial(id);
            return receitaUtilizaMaterialRepository.save(novaRUM);
        }
        return null;
    }

    public List<ReceitaUtilizaMaterial> buscarReceitaUtilizaMaterialPorIdMaterial(Long idMaterial){
        return receitaUtilizaMaterialRepository.findReceitaUtilizaMaterialsByMaterialIdMaterial(idMaterial);
    }
    @Transactional
    public void deleteByReceitaId(Long id) {
        receitaUtilizaMaterialRepository.deleteByReceita_IdReceita(id);
    }
}