package com.example.backlama.controllers;

import com.example.backlama.models.Receita;
import com.example.backlama.services.ReceitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Receita> criarReceita(@RequestBody Receita receita) {
        Receita novaReceita = receitaService.criarReceita(receita);
        return new ResponseEntity<>(novaReceita, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> visualizarReceita(@PathVariable Long id) {
        Receita receita = receitaService.buscarReceitaPorId(id);
        if (receita != null) {
            return new ResponseEntity<>(receita, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Receita> editarReceita(@PathVariable Long id, @RequestBody Receita receita) {
        Receita receitaEditada = receitaService.editarReceita(id, receita);
        if (receitaEditada != null) {
            return new ResponseEntity<>(receitaEditada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
