package com.example.backlama.services;

import com.example.backlama.models.Categoria;
import com.example.backlama.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    public final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    public Categoria criarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    public Categoria buscarCategoriaPorId(Long id) {return categoriaRepository.findById(id).orElse(null);}
    public Categoria editarCategoria(Long id, Categoria categoria) {
        if (categoriaRepository.existsById(id)) {
            return categoriaRepository.save(categoria);
        }
        return null;
    }
}
