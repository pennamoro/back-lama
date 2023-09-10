package com.example.backlama.controllers;

import com.example.backlama.models.Etapas;
import com.example.backlama.services.EtapasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/etapas")
public class EtapasController {
    private final EtapasService etapasService;

    public EtapasController(EtapasService etapasService) {
        this.etapasService = etapasService;
    }
    @PostMapping("/criar")
    public ResponseEntity<Etapas> criarEtapas(@RequestBody Etapas etapas) {
        Etapas novaEtapas = etapasService.criarEtapas(etapas);
        return new ResponseEntity<>(novaEtapas, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Etapas> visualizarEtapas(@PathVariable Long id) {
        Etapas etapas = etapasService.buscarEtapasPorId(id);
        if (etapas != null) {
            return new ResponseEntity<>(etapas, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Etapas> editarEtapas(@PathVariable Long id, @RequestBody Etapas etapas) {
        Etapas etapasEditada = etapasService.editarEtapas(id, etapas);
        if (etapasEditada != null) {
            return new ResponseEntity<>(etapasEditada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
