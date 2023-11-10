package com.example.backlama.controllers;

import com.example.backlama.dto.MaterialDTO;
import com.example.backlama.models.Material;
import com.example.backlama.repositories.TipoRepository;
import com.example.backlama.services.MaterialService;
import com.example.backlama.services.TipoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/material")
public class MaterialController {
    private final MaterialService materialService;
    private final TipoService tipoService;
    public MaterialController(MaterialService materialService, TipoService tipoService){
        this.materialService = materialService;
        this.tipoService = tipoService;
    }
    @PostMapping("/criar")
    public ResponseEntity<Material> createMaterial(@RequestBody MaterialDTO material){
        try{
            Material novoMaterial = new Material();
            novoMaterial.setNome(material.getNome());
            novoMaterial.setTipo(tipoService.buscarPorId(material.getIdTipo()));
            materialService.criarMaterial(novoMaterial);
            return new ResponseEntity<>(novoMaterial, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Material>> visualizarMaterial(){
        try {
            List<Material> material = materialService.mostrarMaterial();
            if (material != null) {
                return new ResponseEntity<>(material, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/tipo/{id}")
    public ResponseEntity<List<Material>> visualizarMaterialPorTipo(@PathVariable Long id){
        try{
            List<Material> materialPorTipo = materialService.bucarMaterialPorIdTipo(id);
            if(materialPorTipo != null){
                return new ResponseEntity<>(materialPorTipo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Material>> filtrarMaterial(@RequestParam(name = "nome", required = false) String nome){
        try {
            List<Material> material = materialService.filtrarMaterial(nome);
            if (material != null) {
                return new ResponseEntity<>(material, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<Material> editarMaterial(@PathVariable Long id){
        try{
            Material material = materialService.buscarMaterialPorId(id);
            if(material == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Material novoMaterial = materialService.editarMaterial(id, material);
            return new ResponseEntity<>(novoMaterial, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable Long id){
        try{
            materialService.deleteMaterial(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}