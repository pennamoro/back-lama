package com.example.backlama.services;

import com.example.backlama.models.Tipo;
import com.example.backlama.repositories.TipoRepository;
import org.springframework.stereotype.Service;

@Service
public class TipoService {
    private final TipoRepository tipoRepository;
    public TipoService(TipoRepository tipoRepository){
        this.tipoRepository = tipoRepository;
    }

    public Tipo buscarPorId(Long idTipo){
        return tipoRepository.findByIdTipo(idTipo);
    }
}
