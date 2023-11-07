package com.example.backlama.controllers;

import com.example.backlama.models.Avaliacao;
import com.example.backlama.services.AvaliacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {
    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService){
        this.avaliacaoService = avaliacaoService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Avaliacao>> listarAvaliacoes(@PathVariable Long id){
        try{
            avaliacaoService.listarPorIdReceita(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
