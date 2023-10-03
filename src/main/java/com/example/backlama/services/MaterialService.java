package com.example.backlama.services;

import com.example.backlama.models.Material;
import com.example.backlama.repositories.MaterialRepository;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    public final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }
    public Material criarMaterial(Material material) {
        return materialRepository.save(material);
    }
    public Material buscarMaterialPorId(Long id) {return materialRepository.findById(id).orElse(null);}
    public Material editarMaterial(Long id, Material material) {
        if (materialRepository.existsById(id)) {
            return materialRepository.save(material);
        }
        return null;
    }
}
