package com.example.backlama.repositories;

import com.example.backlama.models.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    @Query("SELECT r FROM Receita r WHERE (r.nome) LIKE %:nome%")
    List<Receita> filterRecipesByName (String nome);
}