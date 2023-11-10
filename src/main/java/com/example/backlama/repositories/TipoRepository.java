package com.example.backlama.repositories;

import com.example.backlama.models.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {
    Tipo findByIdTipo(Long idTipo);
}
