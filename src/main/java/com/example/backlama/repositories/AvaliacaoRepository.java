package com.example.backlama.repositories;

import com.example.backlama.models.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByReceita_IdReceita(Long id);
    void deleteByReceita_IdReceita(Long idReceita);
}
