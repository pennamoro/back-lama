package com.example.backlama.services;

import com.example.backlama.models.Receita;
import com.example.backlama.repositories.ReceitaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    public List<Receita> listarTodasReceitas() {
        return receitaRepository.findAll();
    }
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
    public Receita atualizarReceita(Receita receita) {
        Long receitaId = receita.getIdReceita();
        if (!receitaRepository.existsById(receitaId)) {
            return null;
        }
        return receitaRepository.save(receita);
    }
    public void deleteReceitaById(Long id){
        receitaRepository.deleteById(id);
    }
}