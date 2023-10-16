package com.example.backlama.services;

import com.example.backlama.models.Etapas;
import com.example.backlama.repositories.EtapasRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtapasService {

    public final EtapasRepository etapasRepository;

    public EtapasService(EtapasRepository etapasRepository) {
        this.etapasRepository = etapasRepository;
    }
    public Etapas criarEtapas(Etapas etapas) {
        return etapasRepository.save(etapas);
    }
    public Etapas buscarEtapasPorId(Long id) {return etapasRepository.findById(id).orElse(null);}

    public List<Etapas> buscarEtapasPorIdReceita(Long idReceita){return etapasRepository.findByReceita_IdReceita(idReceita);}
    public Etapas editarEtapas(Long id, Etapas etapas) {
        if (etapasRepository.existsById(id)) {
            return etapasRepository.save(etapas);
        }
        return null;
    }
    @Transactional
    public void deleteByReceitaId(Long id) {
        etapasRepository.deleteByReceita_IdReceita(id);
    }
}