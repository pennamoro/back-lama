package com.example.backlama.repositories;

import com.example.backlama.models.Passos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassosRepository extends JpaRepository<Passos, Long> {
    List<Passos> findByEtapas_IdEtapas (Long idEtapas);
    void deleteByEtapas_IdEtapas (Long idEtapas);
}
