package com.example.backlama.services;

import com.example.backlama.models.Avaliacao;
import com.example.backlama.repositories.AvaliacaoRepository;
import jakarta.transaction.Transactional;
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
    public Avaliacao criarAvaliacao(Avaliacao avaliacao){
        return avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> listarTodas(){return avaliacaoRepository.findAll();}
    @Transactional
    public void deleteByReceitaId(Long idReceita){avaliacaoRepository.deleteByReceita_IdReceita(idReceita);}
}
