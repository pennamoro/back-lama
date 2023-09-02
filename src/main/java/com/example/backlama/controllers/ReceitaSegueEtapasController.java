package com.example.backlama.controllers;

import com.example.backlama.models.ReceitaSegueEtapas;
import com.example.backlama.services.ReceitaSegueEtapasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receita-segue-etapas")
public class ReceitaSegueEtapasController {
    private final ReceitaSegueEtapasService receitaSegueEtapasService;

    public ReceitaSegueEtapasController(ReceitaSegueEtapasService receitaSegueEtapasService) {
        this.receitaSegueEtapasService = receitaSegueEtapasService;
    }

    @PostMapping()
    public ResponseEntity<ReceitaSegueEtapas> criarPassos(@RequestBody ReceitaSegueEtapas receitaSegueEtapas) {
        ReceitaSegueEtapas novaReceitaSegueEtapas = receitaSegueEtapasService.criarReceitaSegueEtapas(receitaSegueEtapas);
        return new ResponseEntity<>(novaReceitaSegueEtapas, HttpStatus.CREATED);
    }
}
