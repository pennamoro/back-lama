package com.example.backlama.controllers;

import com.example.backlama.dto.EtapasDTO;
import com.example.backlama.dto.ReceitaCriarDTO;
import com.example.backlama.dto.ReceitaDTO;
import com.example.backlama.models.*;
import com.example.backlama.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;
    private final ReceitaUtilizaMaterialService receitaUtilizaMaterialService;
    private final ReceitaSeparadaCategoriaService receitaSeparadaCategoriaService;
    private final ReceitaSegueEtapasService receitaSegueEtapasService;
    private final MaterialService materialService;
    private final CategoriaService categoriaService;
    private final EtapasService etapasService;

    public ReceitaController(ReceitaService receitaService, ReceitaUtilizaMaterialService receitaUtilizaMaterialService, ReceitaSeparadaCategoriaService receitaSeparadaCategoriaService, ReceitaSegueEtapasService receitaSegueEtapasService, MaterialService materialService, CategoriaService categoriaService, EtapasService etapasService) {
        this.receitaService = receitaService;
        this.receitaUtilizaMaterialService = receitaUtilizaMaterialService;
        this.receitaSeparadaCategoriaService = receitaSeparadaCategoriaService;
        this.receitaSegueEtapasService = receitaSegueEtapasService;
        this.materialService = materialService;
        this.categoriaService = categoriaService;
        this.etapasService = etapasService;
    }

    @PostMapping("/criar")
    public ResponseEntity<ReceitaCriarDTO> criarReceita(@RequestBody ReceitaCriarDTO receitaCriarDTO) {
        try {
            Receita receitaCriada = receitaService.criarReceita(receitaCriarDTO.getReceita());

            List<Long> receitaUtilizaMaterialIds = receitaCriarDTO.getReceitaUtilizaMaterialIds();
            List<Long> receitaSeparadaCategoriaIds = receitaCriarDTO.getReceitaSeparadaCategoriaIds();
            List<EtapasDTO> receitaSegueEtapas = receitaCriarDTO.getReceitaSegueEtapas();

            for (Long materialId : receitaUtilizaMaterialIds) {
                ReceitaUtilizaMaterial utilizaMaterial = new ReceitaUtilizaMaterial();
                utilizaMaterial.setReceita(receitaCriada);
                Material material = materialService.buscarMaterialPorId(materialId);
                utilizaMaterial.setMaterial(material);
                receitaUtilizaMaterialService.criarReceitaUtilizaMaterial(utilizaMaterial);
            }

            for (Long categoriaId : receitaSeparadaCategoriaIds) {
                ReceitaSeparadaCategoria separadaCategoria = new ReceitaSeparadaCategoria();
                separadaCategoria.setReceita(receitaCriada);
                Categoria categoria = categoriaService.buscarCategoriaPorId(categoriaId);
                separadaCategoria.setCategoria(categoria);
                receitaSeparadaCategoriaService.criarReceitaSeparadaCategoria(separadaCategoria);
            }

            for (EtapasDTO etapasDTO : receitaSegueEtapas) {
                Etapas etapas = new Etapas();
                etapas.setIdPassos(etapasDTO.getIdPassos());
                etapas.setDescricao(etapasDTO.getDescricao());
                etapasService.criarEtapas(etapas);

                ReceitaSegueEtapas segueEtapas = new ReceitaSegueEtapas();
                segueEtapas.setReceita(receitaCriada);
                segueEtapas.setEtapas(etapas);
                receitaSegueEtapasService.criarReceitaSegueEtapas(segueEtapas);
            }

            return new ResponseEntity<>(receitaCriarDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
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

    @GetMapping("/all")
    public ResponseEntity<List<ReceitaDTO>> listarTodasReceitas() {
        List<Receita> receitas = receitaService.listarTodasReceitas();

        if (!receitas.isEmpty()) {
            List<ReceitaDTO> receitaDTOs = new ArrayList<>();

            for (Receita receita : receitas) {
                List<ReceitaUtilizaMaterial> receitaUtilizaMaterial = receitaUtilizaMaterialService.buscarReceitaUtilizaMaterialPorIdReceita(receita.getIdReceita());
                List<ReceitaSeparadaCategoria> receitaSeparadaCategoria = receitaSeparadaCategoriaService.buscarReceitaSeparadaCategoriaPorIdReceita(receita.getIdReceita());
                List<ReceitaSegueEtapas> receitaSegueEtapas = receitaSegueEtapasService.buscarReceitaSegueEtapasPorIdReceita(receita.getIdReceita());

                ReceitaDTO receitaDTO = new ReceitaDTO();
                receitaDTO.setReceita(receita);
                receitaDTO.setReceitaUtilizaMaterial(receitaUtilizaMaterial);
                receitaDTO.setReceitaSeparadaCategoria(receitaSeparadaCategoria);
                receitaDTO.setReceitaSegueEtapas(receitaSegueEtapas);

                receitaDTOs.add(receitaDTO);
            }

            return new ResponseEntity<>(receitaDTOs, HttpStatus.OK);
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
    @GetMapping("/filter")
    public ResponseEntity<List<ReceitaDTO>> filterReceitasByMaterialAndCategoria(
            @RequestParam(name = "materialId", required = false) Long materialId,
            @RequestParam(name = "categoriaId", required = false) Long categoriaId
    ) {
        List<Receita> receitas = receitaService.listarTodasReceitas();

        if (receitas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<ReceitaDTO> filteredReceitas = new ArrayList<>();

        for (Receita receita : receitas) {
            List<ReceitaUtilizaMaterial> receitaUtilizaMaterial = receitaUtilizaMaterialService.buscarReceitaUtilizaMaterialPorIdReceita(receita.getIdReceita());
            if (materialId != null) {
                receitaUtilizaMaterial = receitaUtilizaMaterial.stream()
                        .filter(rum -> rum.getMaterial().getId_material().equals(materialId))
                        .collect(Collectors.toList());
            }

            List<ReceitaSeparadaCategoria> receitaSeparadaCategoria = receitaSeparadaCategoriaService.buscarReceitaSeparadaCategoriaPorIdReceita(receita.getIdReceita());
            if (categoriaId != null) {
                receitaSeparadaCategoria = receitaSeparadaCategoria.stream()
                        .filter(rsc -> rsc.getCategoria().getIdCategoria().equals(categoriaId))
                        .collect(Collectors.toList());
            }

            if ((materialId == null || !receitaUtilizaMaterial.isEmpty()) &&
                    (categoriaId == null || !receitaSeparadaCategoria.isEmpty())) {
                ReceitaDTO receitaDTO = new ReceitaDTO();
                receitaDTO.setReceita(receita);
                receitaDTO.setReceitaUtilizaMaterial(receitaUtilizaMaterial);
                receitaDTO.setReceitaSeparadaCategoria(receitaSeparadaCategoria);
                filteredReceitas.add(receitaDTO);
            }
        }

        if (filteredReceitas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(filteredReceitas, HttpStatus.OK);
    }
}