package com.example.backlama.repositories;

import com.example.backlama.models.Etapas;
import com.example.backlama.models.EtapasId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtapasRepository extends JpaRepository<Etapas, EtapasId> {
}
