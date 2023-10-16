package com.example.backlama.repositories;

import com.example.backlama.models.ReceitaSeparadaCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReceitaSeparadaCategoriaRepository extends JpaRepository<ReceitaSeparadaCategoria, Long> {
    List<ReceitaSeparadaCategoria> findByReceita_IdReceita(Long idReceita);
    void deleteByReceita_IdReceita(Long receitaId);
    List<ReceitaSeparadaCategoria> findReceitaSeparadaCategoriasByCategoriaIdCategoria(Long idCategoria);
}