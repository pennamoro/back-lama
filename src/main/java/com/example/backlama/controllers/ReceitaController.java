package com.example.backlama.controllers;

import com.example.backlama.dto.ReceitaDTO;
import com.example.backlama.models.Receita;
import com.example.backlama.models.ReceitaSegueEtapas;
import com.example.backlama.models.ReceitaSeparadaCategoria;
import com.example.backlama.models.ReceitaUtilizaMaterial;
import com.example.backlama.services.ReceitaSegueEtapasService;
import com.example.backlama.services.ReceitaSeparadaCategoriaService;
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
    private final ReceitaSeparadaCategoriaService receitaSeparadaCategoriaService;
    private final ReceitaSegueEtapasService receitaSegueEtapasService;


    public ReceitaController(ReceitaService receitaService, ReceitaUtilizaMaterialService receitaUtilizaMaterialService, ReceitaSeparadaCategoriaService receitaSeparadaCategoriaService, ReceitaSegueEtapasService receitaSegueEtapasService) {
        this.receitaService = receitaService;
        this.receitaUtilizaMaterialService = receitaUtilizaMaterialService;
        this.receitaSeparadaCategoriaService = receitaSeparadaCategoriaService;
        this.receitaSegueEtapasService = receitaSegueEtapasService;
    }

    @PostMapping("/criar")
    public ResponseEntity<ReceitaDTO> criarReceita(@RequestBody ReceitaDTO receitaDTO) {
        try {
            Receita receitaCriada = receitaService.criarReceita(receitaDTO.getReceita());

            List<ReceitaUtilizaMaterial> receitaUtilizaMaterialsList = receitaDTO.getReceitaUtilizaMaterial();
            List<ReceitaSeparadaCategoria> receitaSeparadaCategoriaList = receitaDTO.getReceitaSeparadaCategoria();
            List<ReceitaSegueEtapas> receitaSegueEtapasList = receitaDTO.getReceitaSegueEtapas();

            for (ReceitaUtilizaMaterial utilizaMaterial : receitaUtilizaMaterialsList) {
                utilizaMaterial.setReceita(receitaCriada);
                receitaUtilizaMaterialService.criarReceitaUtilizaMaterial(utilizaMaterial);
            }
            for (ReceitaSeparadaCategoria separadaCategoria : receitaSeparadaCategoriaList){
                separadaCategoria.setReceita(receitaCriada);
                receitaSeparadaCategoriaService.criarReceitaSeparadaCategoria(separadaCategoria);
            }
            for (ReceitaSegueEtapas segueEtapas : receitaSegueEtapasList){
                segueEtapas.setReceita(receitaCriada);
                receitaSegueEtapasService.criarReceitaSegueEtapas(segueEtapas);
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
        List<ReceitaSeparadaCategoria> receitaSeparadaCategoria = receitaSeparadaCategoriaService.buscarReceitaSeparadaCategoriaPorIdReceita(id);
        List<ReceitaSegueEtapas> receitaSegueEtapas = receitaSegueEtapasService.buscarReceitaSegueEtapasPorIdReceita(id);

        if (receita != null) {
            ReceitaDTO receitaDTO = new ReceitaDTO();

            receitaDTO.setReceita(receita);
            receitaDTO.setReceitaUtilizaMaterial(receitaUtilizaMaterial);
            receitaDTO.setReceitaSeparadaCategoria(receitaSeparadaCategoria);
            receitaDTO.setReceitaSegueEtapas(receitaSegueEtapas);

            return new ResponseEntity<>(receitaDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ReceitaDTO> editarReceita(@PathVariable Long id, @RequestBody ReceitaDTO receitaDTO) {
        try {
            Receita existingReceita = receitaService.buscarReceitaPorId(id);
            if (existingReceita == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            existingReceita.setNome(receitaDTO.getReceita().getNome());
            existingReceita.setFoto(receitaDTO.getReceita().getFoto());
            existingReceita.setNivelExperiencia(receitaDTO.getReceita().getNivelExperiencia());
            existingReceita.setVisibilidade(receitaDTO.getReceita().getVisibilidade());
            existingReceita.setCores(receitaDTO.getReceita().getCores());

            Receita updatedReceita = receitaService.editarReceita(id, existingReceita);

            List<ReceitaUtilizaMaterial> receitaUtilizaMaterialsList = receitaDTO.getReceitaUtilizaMaterial();
            List<ReceitaSeparadaCategoria> receitaSeparadaCategoriaList = receitaDTO.getReceitaSeparadaCategoria();
            List<ReceitaSegueEtapas> receitaSegueEtapasList = receitaDTO.getReceitaSegueEtapas();

            for (ReceitaUtilizaMaterial utilizaMaterial : receitaUtilizaMaterialsList) {
                utilizaMaterial.setReceita(updatedReceita);
                receitaUtilizaMaterialService.criarReceitaUtilizaMaterial(utilizaMaterial);
            }
            for (ReceitaSeparadaCategoria separadaCategoria : receitaSeparadaCategoriaList) {
                separadaCategoria.setReceita(updatedReceita);
                receitaSeparadaCategoriaService.criarReceitaSeparadaCategoria(separadaCategoria);
            }
            for (ReceitaSegueEtapas segueEtapas : receitaSegueEtapasList) {
                segueEtapas.setReceita(updatedReceita);
                receitaSegueEtapasService.criarReceitaSegueEtapas(segueEtapas);
            }

            return new ResponseEntity<>(receitaDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
