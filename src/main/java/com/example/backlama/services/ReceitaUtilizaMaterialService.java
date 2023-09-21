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
    public List<ReceitaUtilizaMaterial> editarReceitaUtilizaMaterial(Long idReceita, List<ReceitaUtilizaMaterial> novosMateriais) {
        List<ReceitaUtilizaMaterial> receitaUtilizaMaterialList = receitaUtilizaMaterialRepository.findByReceita_IdReceita(idReceita);

        if (receitaUtilizaMaterialList != null) {
            for (ReceitaUtilizaMaterial novoMaterial : novosMateriais) {
                for (ReceitaUtilizaMaterial materialExistente : receitaUtilizaMaterialList) {
                    if (Objects.equals(materialExistente.getIdMaterial(), novoMaterial.getIdMaterial())) {
                        materialExistente.setMaterial(novoMaterial.getMaterial());
                        materialExistente.setReceita(novoMaterial.getReceita());
                    }
                }
            }
            receitaUtilizaMaterialRepository.saveAll(receitaUtilizaMaterialList);
            return receitaUtilizaMaterialList;
        }
        return null;
    }
}

