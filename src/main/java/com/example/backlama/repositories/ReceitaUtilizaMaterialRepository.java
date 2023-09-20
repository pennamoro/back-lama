package com.example.backlama.repositories;

import com.example.backlama.models.ReceitaUtilizaMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaUtilizaMaterialRepository extends JpaRepository<ReceitaUtilizaMaterial, Long> {
    List<ReceitaUtilizaMaterial> findByReceita_IdReceita(Long idReceita);
}
