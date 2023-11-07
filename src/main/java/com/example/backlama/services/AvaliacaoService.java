package com.example.backlama.services;

import com.example.backlama.models.Avaliacao;
import com.example.backlama.repositories.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {
    public final AvaliacaoRepository avaliacaoRepository;
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository){
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public List<Avaliacao> listarPorIdReceita(Long id){
        return avaliacaoRepository.findByReceita_IdReceita(id);
    }
}
