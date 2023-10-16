package com.example.backlama.repositories;

import com.example.backlama.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findMaterialByTipo_IdTipo(Long id_tipo);
    @Query("SELECT m FROM Material m WHERE LOWER(m.nome) LIKE %:nome%")
    List<Material> findMaterialByNome(@Param("nome") String nome);
}