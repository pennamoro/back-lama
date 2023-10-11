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
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;
    private final ReceitaUtilizaMaterialService receitaUtilizaMaterialService;
    private final ReceitaSeparadaCategoriaService receitaSeparadaCategoriaService;
    private final MaterialService materialService;
    private final CategoriaService categoriaService;
    private final EtapasService etapasService;
    private final PassosService passosService;
    private final UserService userService;

    public ReceitaController(ReceitaService receitaService, ReceitaUtilizaMaterialService receitaUtilizaMaterialService, ReceitaSeparadaCategoriaService receitaSeparadaCategoriaService, MaterialService materialService, CategoriaService categoriaService, EtapasService etapasService, PassosService passosService, UserService userService) {
        this.receitaService = receitaService;
        this.receitaUtilizaMaterialService = receitaUtilizaMaterialService;
        this.receitaSeparadaCategoriaService = receitaSeparadaCategoriaService;
        this.materialService = materialService;
        this.categoriaService = categoriaService;
        this.etapasService = etapasService;
        this.passosService = passosService;
        this.userService = userService;
    }

    @PostMapping("/criar")
    public ResponseEntity<ReceitaCriarDTO> criarReceita(@RequestBody ReceitaCriarDTO receitaCriarDTO) {
        try {
            User user = userService.buscarUserById(receitaCriarDTO.getUserId());
            Receita receita = receitaCriarDTO.getReceita();
            receita.setUser(user);
            Receita receitaCriada = receitaService.criarReceita(receita);

            List<Long> receitaUtilizaMaterialIds = receitaCriarDTO.getReceitaUtilizaMaterialIds();
            List<Long> receitaSeparadaCategoriaIds = receitaCriarDTO.getReceitaSeparadaCategoriaIds();
            List<EtapasDTO> receitaSegueEtapas = receitaCriarDTO.getEtapas();

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
                etapas.setDescricao(etapasDTO.getDescricao_etapas());
                etapas.setReceita(receita);
                etapasService.criarEtapas(etapas);
                for (Passos passos : etapasDTO.getPassosList()) {
                    Passos passo = new Passos();
                    passo.setDescricao(passos.getDescricao());
                    passo.setEtapas(etapas);
                    passosService.criarPassos(passo);
                }
            }
            receitaCriarDTO.getReceita().getUser().setSenha(null);
            return new ResponseEntity<>(receitaCriarDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceitaDTO> visualizarReceita(@PathVariable Long id) {
        Receita receita = receitaService.buscarReceitaPorId(id);

        if (receita != null) {
            List<ReceitaUtilizaMaterial> receitaUtilizaMaterial = receitaUtilizaMaterialService.buscarReceitaUtilizaMaterialPorIdReceita(id);
            List<ReceitaSeparadaCategoria> receitaSeparadaCategoria = receitaSeparadaCategoriaService.buscarReceitaSeparadaCategoriaPorIdReceita(id);
            List<Etapas> etapas = etapasService.buscarEtapasPorIdReceita(id);

            ReceitaDTO receitaDTO = new ReceitaDTO();
            receita.getUser().setSenha(null);
            receitaDTO.setReceita(receita);
            receitaDTO.setReceitaUtilizaMaterial(receitaUtilizaMaterial);
            receitaDTO.setReceitaSeparadaCategoria(receitaSeparadaCategoria);

            List<EtapasDTO> etapasDTOList = new ArrayList<>();

            for (Etapas etapa : etapas) {
                List<Passos> passos = passosService.buscarPassosPorIdEtapas(etapa.getIdEtapas());
                EtapasDTO etapasDTO = new EtapasDTO();
                etapasDTO.setPassosList(passos);
                etapasDTO.setDescricao_etapas(etapa.getDescricao());
                etapasDTOList.add(etapasDTO);
            }

            receitaDTO.setEtapas(etapasDTOList);
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
                List<Etapas> etapas = etapasService.buscarEtapasPorIdReceita(receita.getIdReceita());

                List<EtapasDTO> etapasDTOList = new ArrayList<>();

                for (Etapas etapa : etapas) {
                    List<Passos> passos = passosService.buscarPassosPorIdEtapas(etapa.getIdEtapas());
                    EtapasDTO etapasDTO = new EtapasDTO();
                    etapasDTO.setPassosList(passos);
                    etapasDTO.setDescricao_etapas(etapa.getDescricao());
                    etapasDTOList.add(etapasDTO);
                }

                ReceitaDTO receitaDTO = new ReceitaDTO();
                receita.getUser().setSenha(null);
                receitaDTO.setReceita(receita);
                receitaDTO.setReceitaUtilizaMaterial(receitaUtilizaMaterial);
                receitaDTO.setReceitaSeparadaCategoria(receitaSeparadaCategoria);
                receitaDTO.setEtapas(etapasDTOList);

                receitaDTOs.add(receitaDTO);
            }

            return new ResponseEntity<>(receitaDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<ReceitaDTO> editarReceita(@PathVariable Long id, @RequestBody ReceitaCriarDTO receitaCriarDTO) {
        try {
            Receita existingReceita = receitaService.buscarReceitaPorId(id);
            if (existingReceita == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            User user = userService.buscarUserById(receitaCriarDTO.getUserId());
            existingReceita.setNome(receitaCriarDTO.getReceita().getNome());
            existingReceita.setUser(user);

            List<Long> receitaUtilizaMaterialIds = receitaCriarDTO.getReceitaUtilizaMaterialIds();
            receitaUtilizaMaterialService.deleteByReceitaId(id);
            for (Long materialId : receitaUtilizaMaterialIds) {
                ReceitaUtilizaMaterial utilizaMaterial = new ReceitaUtilizaMaterial();
                utilizaMaterial.setReceita(existingReceita);
                Material material = materialService.buscarMaterialPorId(materialId);
                utilizaMaterial.setMaterial(material);
                receitaUtilizaMaterialService.criarReceitaUtilizaMaterial(utilizaMaterial);
            }

            List<Long> receitaSeparadaCategoriaIds = receitaCriarDTO.getReceitaSeparadaCategoriaIds();
            receitaSeparadaCategoriaService.deleteByReceitaId(id);
            for (Long categoriaId : receitaSeparadaCategoriaIds) {
                ReceitaSeparadaCategoria separadaCategoria = new ReceitaSeparadaCategoria();
                separadaCategoria.setReceita(existingReceita);
                Categoria categoria = categoriaService.buscarCategoriaPorId(categoriaId);
                separadaCategoria.setCategoria(categoria);
                receitaSeparadaCategoriaService.criarReceitaSeparadaCategoria(separadaCategoria);
            }

            List<EtapasDTO> receitaSegueEtapas = receitaCriarDTO.getEtapas();

            List<Etapas> etapasList1 = etapasService.buscarEtapasPorIdReceita(existingReceita.getIdReceita());

            for (Etapas etapas : etapasList1) {
                Long etapasId = etapas.getIdEtapas();
                passosService.deleteByEtapasId(etapasId);
            }
            etapasService.deleteByReceitaId(id);
            for (EtapasDTO etapasDTO : receitaSegueEtapas) {
                Etapas etapas = new Etapas();
                etapas.setDescricao(etapasDTO.getDescricao_etapas());
                etapas.setReceita(existingReceita);
                etapasService.criarEtapas(etapas);
                for (Passos passos : etapasDTO.getPassosList()) {
                    Passos passo = new Passos();
                    passo.setDescricao(passos.getDescricao());
                    passo.setEtapas(etapas);
                    passosService.criarPassos(passo);
                }
            }

            Receita updatedReceita = receitaService.atualizarReceita(existingReceita);

            ReceitaDTO updatedReceitaDTO = new ReceitaDTO();
            updatedReceita.getUser().setSenha(null);
            updatedReceitaDTO.setReceita(updatedReceita);
            updatedReceitaDTO.setReceitaUtilizaMaterial(receitaUtilizaMaterialService.buscarReceitaUtilizaMaterialPorIdReceita(id));
            updatedReceitaDTO.setReceitaSeparadaCategoria(receitaSeparadaCategoriaService.buscarReceitaSeparadaCategoriaPorIdReceita(id));
            List<Etapas> etapasList = etapasService.buscarEtapasPorIdReceita(id);
            List<EtapasDTO> etapasDTOList = etapasList.stream()
                    .map(etapas -> {
                        EtapasDTO etapasDTO = new EtapasDTO();
                        etapasDTO.setPassosList(passosService.buscarPassosPorIdEtapas(etapas.getIdEtapas()));
                        etapasDTO.setDescricao_etapas(etapas.getDescricao());
                        return etapasDTO;
                    })
                    .collect(Collectors.toList());

            updatedReceitaDTO.setEtapas(etapasDTOList);

            return new ResponseEntity<>(updatedReceitaDTO, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Receita>> filterRecipes(
            @RequestParam(name = "idMaterial", required = false) Long idMaterial,
            @RequestParam(name = "idCategoria", required = false) Long idCategoria
    ) {
        try {
            List<Receita> receitaList = new ArrayList<>();
            List<Receita> todasReceitas = receitaService.listarTodasReceitas();

            if (idMaterial == null && idCategoria == null) {
                return new ResponseEntity<>(todasReceitas, HttpStatus.OK);
            }

            List<Receita> combinedRecipes = new ArrayList<>();

            if (idCategoria != null) {
                List<ReceitaSeparadaCategoria> categorias = receitaSeparadaCategoriaService.buscarReceitaSeparadaCategoriaPorIdCategoria(idCategoria);
                for (ReceitaSeparadaCategoria categoria : categorias) {
                    if (!combinedRecipes.contains(categoria.getReceita())) {
                        combinedRecipes.add(categoria.getReceita());
                    }
                }
            }

            if (idMaterial != null) {
                List<ReceitaUtilizaMaterial> materiais = receitaUtilizaMaterialService.buscarReceitaUtilizaMaterialPorIdMaterial(idMaterial);
                for (ReceitaUtilizaMaterial material : materiais) {
                    if (!combinedRecipes.contains(material.getReceita())) {
                        combinedRecipes.add(material.getReceita());
                    }
                }
            }
            for (Receita receita : todasReceitas) {
                if (combinedRecipes.contains(receita)) {
                    receitaList.add(receita);
                }
            }

            if (!receitaList.isEmpty()) {
                return new ResponseEntity<>(receitaList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteReceita(@PathVariable Long id){
        try {
            Receita existingReceita = receitaService.buscarReceitaPorId(id);
            if (existingReceita == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            receitaUtilizaMaterialService.deleteByReceitaId(id);

            receitaSeparadaCategoriaService.deleteByReceitaId(id);

            List<Etapas> etapasList1 = etapasService.buscarEtapasPorIdReceita(existingReceita.getIdReceita());

            for (Etapas etapas : etapasList1) {
                Long etapasId = etapas.getIdEtapas();
                passosService.deleteByEtapasId(etapasId);
            }
            etapasService.deleteByReceitaId(id);
            receitaService.deleteReceitaById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}