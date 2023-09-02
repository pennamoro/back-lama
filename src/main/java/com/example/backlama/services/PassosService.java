package com.example.backlama.services;

import com.example.backlama.models.Passos;
import com.example.backlama.repositories.PassosRepository;
import org.springframework.stereotype.Service;

@Service
public class PassosService {
    public final PassosRepository passosRepository;

    public PassosService(PassosRepository passosRepository) {
        this.passosRepository = passosRepository;
    }
    public Passos criarPassos(Passos passos) {
        return passosRepository.save(passos);
    }
    public Passos buscarPassosPorId(Long id) {
        return passosRepository.findById(id).orElse(null);
    }
    public Passos editarPassos(Long id, Passos passos) {
        if (passosRepository.existsById(id)) {
            passos.setIdPassos(id);
            return passosRepository.save(passos);
        }
        return null;
    }
}
