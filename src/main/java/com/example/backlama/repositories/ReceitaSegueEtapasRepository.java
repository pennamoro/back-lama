package com.example.backlama.repositories;

import com.example.backlama.models.ReceitaSegueEtapas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceitaSegueEtapasRepository extends JpaRepository<ReceitaSegueEtapas, Long> {
    List<ReceitaSegueEtapas> findByReceita_IdReceita(Long idReceita);
}
