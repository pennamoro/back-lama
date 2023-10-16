package com.example.backlama.services;

import com.example.backlama.models.UsuarioPossuiMaterial;
import com.example.backlama.repositories.UsuarioPossuiMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioPossuiMaterialService {
    public final UsuarioPossuiMaterialRepository usuarioPossuiMaterialRepository;

    public UsuarioPossuiMaterialService(UsuarioPossuiMaterialRepository usuarioPossuiMaterialRepository){
        this.usuarioPossuiMaterialRepository = usuarioPossuiMaterialRepository;
    }
    public List<UsuarioPossuiMaterial> buscarPorIdUsuario(Long idUsuario){
        return usuarioPossuiMaterialRepository.findByUsuario_IdUsuario(idUsuario);
    }
    public UsuarioPossuiMaterial criarUsuarioPossuiMaterial(UsuarioPossuiMaterial usuarioPossuiMaterial){
        return  usuarioPossuiMaterialRepository.save(usuarioPossuiMaterial);
    }
}