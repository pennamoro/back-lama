package com.example.backlama.services;

import com.example.backlama.models.Material;
import com.example.backlama.repositories.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Material> bucarMaterialPorIdTipo(Long id_tipo) {return materialRepository.findMaterialByTipo_IdTipo(id_tipo);}
    public Material editarMaterial(Long id, Material material) {
        if (materialRepository.existsById(id)) {
            return materialRepository.save(material);
        }
        return null;
    }
    public List<Material> mostrarMaterial(){return materialRepository.findAll();}
    public List<Material> filtrarMaterial(String nome){return materialRepository.findMaterialByNome(nome);}
}