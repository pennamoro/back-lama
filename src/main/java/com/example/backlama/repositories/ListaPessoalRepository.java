package com.example.backlama.repositories;

import com.example.backlama.models.ListaPessoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaPessoalRepository extends JpaRepository<ListaPessoal, Long> {
    List<ListaPessoal> findByUsuario_IdUsuario(Long idUsuario);
    void deleteByUsuario_IdUsuario(Long idUsuario);
}