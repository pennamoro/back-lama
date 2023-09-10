package com.example.backlama.repositories;

import com.example.backlama.models.ReceitaSegueEtapas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceitaSegueEtapasRepository extends JpaRepository<ReceitaSegueEtapas, Long> {
}
