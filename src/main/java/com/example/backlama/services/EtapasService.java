package com.example.backlama.services;

import com.example.backlama.models.Etapas;
import com.example.backlama.models.EtapasId;
import com.example.backlama.repositories.EtapasRepository;
import org.springframework.stereotype.Service;

@Service
public class EtapasService {

    public final EtapasRepository etapasRepository;

    public EtapasService(EtapasRepository etapasRepository) {
        this.etapasRepository = etapasRepository;
    }
    public Etapas criarEtapas(Etapas etapas) {
        return etapasRepository.save(etapas);
    }
    public Etapas buscarEtapasPorId(EtapasId etapasId) {
        return etapasRepository.findById(etapasId).orElse(null);
    }

    public Etapas editarEtapas(EtapasId etapasId, Etapas etapas) {
        if (etapasRepository.existsById(etapasId)) {
            etapas.setIdEtapas(etapasId.getIdEtapas());
            etapas.setIdPassos(etapasId.getIdPassos());
            return etapasRepository.save(etapas);
        }
        return null;
    }
}
