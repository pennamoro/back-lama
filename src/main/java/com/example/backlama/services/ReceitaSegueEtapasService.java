package com.example.backlama.services;

import com.example.backlama.models.ReceitaSegueEtapas;
import com.example.backlama.repositories.ReceitaSegueEtapasRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceitaSegueEtapasService {
    public final ReceitaSegueEtapasRepository receitaSegueEtapasRepository;

    public ReceitaSegueEtapasService(ReceitaSegueEtapasRepository receitaSegueEtapasRepository) {
        this.receitaSegueEtapasRepository = receitaSegueEtapasRepository;
    }
    public ReceitaSegueEtapas criarReceitaSegueEtapas(ReceitaSegueEtapas receitaSegueEtapas) {
        return receitaSegueEtapasRepository.save(receitaSegueEtapas);
    }
}
