package com.example.backlama.services;

import com.example.backlama.models.ReceitaSeparadaCategoria;
import com.example.backlama.repositories.ReceitaSeparadaCategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReceitaSeparadaCategoriaService {
    public final ReceitaSeparadaCategoriaRepository receitaSeparadaCategoriaRepository;

    public ReceitaSeparadaCategoriaService(ReceitaSeparadaCategoriaRepository receitaSeparadaCategoriaRepository) {
        this.receitaSeparadaCategoriaRepository = receitaSeparadaCategoriaRepository;
    }
    public void criarReceitaSeparadaCategoria(ReceitaSeparadaCategoria receitaSeparadaCategoria) {
        receitaSeparadaCategoriaRepository.save(receitaSeparadaCategoria);
    }

    public ReceitaSeparadaCategoria buscarReceitaSeparadaCategoriaPorId(Long id) {
        return receitaSeparadaCategoriaRepository.findById(id).orElse(null);
    }
    public List<ReceitaSeparadaCategoria> buscarReceitaSeparadaCategoriaPorIdReceita(Long idReceita) {
        return receitaSeparadaCategoriaRepository.findByReceita_IdReceita(idReceita);
    }
    public List<ReceitaSeparadaCategoria> editarReceitaSeparadaCategoria(Long idReceita, List<ReceitaSeparadaCategoria> novasCategorias) {
        List<ReceitaSeparadaCategoria> receitaSeparadaCategoriaList = receitaSeparadaCategoriaRepository.findByReceita_IdReceita(idReceita);

        if (receitaSeparadaCategoriaList != null) {
            for (ReceitaSeparadaCategoria novaCategoria : novasCategorias) {
                for (ReceitaSeparadaCategoria categoriasExistentes : receitaSeparadaCategoriaList) {
                    if (Objects.equals(categoriasExistentes.getCategoria(), novaCategoria.getCategoria())) {
                        categoriasExistentes.setCategoria(novaCategoria.getCategoria());
                        categoriasExistentes.setReceita(novaCategoria.getReceita());
                    }
                }
            }
            receitaSeparadaCategoriaRepository.saveAll(receitaSeparadaCategoriaList);
            return receitaSeparadaCategoriaList;
        }
        return null;
    }
}