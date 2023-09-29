package com.example.backlama.services;

import com.example.backlama.models.ReceitaSegueEtapas;
import com.example.backlama.repositories.ReceitaSegueEtapasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ReceitaSegueEtapas editarReceitaSegueEtapas(Long id, ReceitaSegueEtapas novaRSE) {
        if (receitaSegueEtapasRepository.existsById(id)) {
            novaRSE.setId_receita_segue_etapas(id);
            return receitaSegueEtapasRepository.save(novaRSE);
        }
        return null;
    }
}