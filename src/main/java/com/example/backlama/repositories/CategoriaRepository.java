package com.example.backlama.repositories;

import com.example.backlama.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.descricao) LIKE %:descricao%")
    List<Categoria> findCategoriaByDescricao(@Param("descricao") String descricao);
}
