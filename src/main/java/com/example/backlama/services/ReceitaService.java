package com.example.backlama.services;

import com.example.backlama.models.Receita;
import com.example.backlama.repositories.ReceitaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    public Receita criarReceita(Receita receita) {
        return receitaRepository.save(receita);
    }

    public Receita buscarReceitaPorId(Long id) {
        return receitaRepository.findById(id).orElse(null);
    }

    public Receita editarReceita(Long id, Receita receita) {
        if (receitaRepository.existsById(id)) {
            receita.setIdReceita(id);
            return receitaRepository.save(receita);
        }
        return null;
    }
}

