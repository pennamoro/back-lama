package com.example.backlama.services;

import com.example.backlama.models.ReceitaSegueEtapas;
import com.example.backlama.repositories.ReceitaSegueEtapasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReceitaSegueEtapasService {
    public final ReceitaSegueEtapasRepository receitaSegueEtapasRepository;

    public ReceitaSegueEtapasService(ReceitaSegueEtapasRepository receitaSegueEtapasRepository) {
        this.receitaSegueEtapasRepository = receitaSegueEtapasRepository;
    }
    public void criarReceitaSegueEtapas(ReceitaSegueEtapas receitaSegueEtapas) {
        receitaSegueEtapasRepository.save(receitaSegueEtapas);
    }

    public ReceitaSegueEtapas buscarReceitaSegueEtapasPorId(Long id) {
        return receitaSegueEtapasRepository.findById(id).orElse(null);
    }
    public List<ReceitaSegueEtapas> buscarReceitaSegueEtapasPorIdReceita(Long idReceita) {
        return receitaSegueEtapasRepository.findByReceita_IdReceita(idReceita);
    }

    public List<ReceitaSegueEtapas> editarReceitaSegueEtapas(Long idReceita, List<ReceitaSegueEtapas> novasEtapas) {
        List<ReceitaSegueEtapas> receitaSegueEtapasList = receitaSegueEtapasRepository.findByReceita_IdReceita(idReceita);

        if (receitaSegueEtapasList != null) {
            for (ReceitaSegueEtapas novaEtapa : novasEtapas) {
                for (ReceitaSegueEtapas etapasExistentes : receitaSegueEtapasList) {
                    if (Objects.equals(etapasExistentes.getEtapas(), novaEtapa.getEtapas())) {
                        etapasExistentes.setEtapas(novaEtapa.getEtapas());
                        etapasExistentes.setReceita(novaEtapa.getReceita());
                    }
                }
            }
            receitaSegueEtapasRepository.saveAll(receitaSegueEtapasList);
            return receitaSegueEtapasList;
        }
        return null;
    }
}