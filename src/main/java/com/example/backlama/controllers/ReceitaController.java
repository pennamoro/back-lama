package com.example.backlama.controllers;

import com.example.backlama.dto.ReceitaDTO;
import com.example.backlama.models.Receita;
import com.example.backlama.models.ReceitaUtilizaMaterial;
import com.example.backlama.services.ReceitaService;
import com.example.backlama.services.ReceitaUtilizaMaterialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;
    private final ReceitaUtilizaMaterialService receitaUtilizaMaterialService;

    public ReceitaController(ReceitaService receitaService, ReceitaUtilizaMaterialService receitaUtilizaMaterialService) {
        this.receitaService = receitaService;
        this.receitaUtilizaMaterialService = receitaUtilizaMaterialService;
    }

    @PostMapping("/criar")
    public ResponseEntity<ReceitaDTO> criarReceita(@RequestBody ReceitaDTO receitaDTO) {
        try {
            Receita receitaCriada = receitaService.criarReceita(receitaDTO.getReceita());

            List<ReceitaUtilizaMaterial> receitaUtilizaMaterialsList = receitaDTO.getReceitaUtilizaMaterial();

            for (ReceitaUtilizaMaterial utilizaMaterial : receitaUtilizaMaterialsList) {
                utilizaMaterial.setReceita(receitaCriada);
                receitaUtilizaMaterialService.criarReceitaUtilizaMaterial(utilizaMaterial);
            }

            return new ResponseEntity<>(receitaDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> visualizarReceita(@PathVariable Long id) {
        Receita receita = receitaService.buscarReceitaPorId(id);
        List<ReceitaUtilizaMaterial> receitaUtilizaMaterial = receitaUtilizaMaterialService.buscarReceitaUtilizaMaterialPorIdReceita(id);
        if (receita != null) {
            ReceitaDTO receitaDTO = new ReceitaDTO();
            receitaDTO.setReceita(receita);
            receitaDTO.setReceitaUtilizaMaterial(receitaUtilizaMaterial);
            return new ResponseEntity<>(receitaDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ReceitaDTO> editarReceita(@PathVariable Long id, @RequestBody Receita receita) {
        Receita receitaEditada = receitaService.editarReceita(id, receita);
        if (receitaEditada != null) {
            ReceitaDTO receitaDTO = new ReceitaDTO();
            receitaDTO.setReceita(receitaEditada);
            return new ResponseEntity<>(receitaDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
