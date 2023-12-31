package com.example.backlama.controllers;

import com.example.backlama.models.Categoria;
import com.example.backlama.services.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    private final CategoriaService categoriaService;
    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Categoria>> listarCategorias(){
        try {
            List<Categoria> categorias = categoriaService.mostrarCategoria();
            if (categorias != null) {
                return new ResponseEntity<>(categorias, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}