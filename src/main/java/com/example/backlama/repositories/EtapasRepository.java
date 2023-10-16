package com.example.backlama.repositories;

import com.example.backlama.models.Etapas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtapasRepository extends JpaRepository<Etapas, Long> {
    List<Etapas> findByReceita_IdReceita(Long idReceita);
    void deleteByReceita_IdReceita(Long receitaId);
}