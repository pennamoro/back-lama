package com.example.backlama.services;

import com.example.backlama.models.ReceitaSeparadaCategoria;
import com.example.backlama.repositories.ReceitaSeparadaCategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaSeparadaCategoriaService {
    public final ReceitaSeparadaCategoriaRepository receitaSeparadaCategoriaRepository;

    public ReceitaSeparadaCategoriaService(ReceitaSeparadaCategoriaRepository receitaSeparadaCategoriaRepository) {
        this.receitaSeparadaCategoriaRepository = receitaSeparadaCategoriaRepository;
    }
    public void criarReceitaSeparadaCategoria(ReceitaSeparadaCategoria receitaSeparadaCategoria) {
        receitaSeparadaCategoriaRepository.save(receitaSeparadaCategoria);
    }
    public List<ReceitaSeparadaCategoria> buscarReceitaSeparadaCategoriaPorIdReceita(Long idReceita) {
        return receitaSeparadaCategoriaRepository.findByReceita_IdReceita(idReceita);
    }
    public List<ReceitaSeparadaCategoria> buscarReceitaSeparadaCategoriaPorIdCategoria(Long idCategoria){
        return receitaSeparadaCategoriaRepository.findReceitaSeparadaCategoriasByCategoriaIdCategoria(idCategoria);
    }
    @Transactional
    public void deleteByReceitaId(Long id) {
        receitaSeparadaCategoriaRepository.deleteByReceita_IdReceita(id);
    }

    public void deleteAll(){
        receitaSeparadaCategoriaRepository.deleteAll();
    }

    public List<ReceitaSeparadaCategoria> listAll(){return receitaSeparadaCategoriaRepository.findAll();}
}