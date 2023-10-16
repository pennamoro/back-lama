package com.example.backlama.repositories;

import com.example.backlama.models.UsuarioPossuiMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioPossuiMaterialRepository extends JpaRepository<UsuarioPossuiMaterial, Long> {
    List<UsuarioPossuiMaterial> findByUsuario_IdUsuario(Long idUsuario);
}