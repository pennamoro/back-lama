package com.example.backlama.repositories;

import com.example.backlama.models.Passos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassosRepository extends JpaRepository<Passos, Long> {
}
