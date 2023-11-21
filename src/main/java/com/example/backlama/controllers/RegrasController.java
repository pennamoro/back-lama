package com.example.backlama.controllers;

import com.example.backlama.dto.RegrasAssociacaoDTO;
import com.example.backlama.services.RegraAssociacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/regras")
public class RegrasController {
    private final RegraAssociacaoService regraAssociacaoService;
    public RegrasController(RegraAssociacaoService regraAssociacaoService){
        this.regraAssociacaoService = regraAssociacaoService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<RegrasAssociacaoDTO>> listarTodas(){
        try{
            List<RegrasAssociacaoDTO> regrasAssociacaoDTOList =  regraAssociacaoService.lerRegrasDeAssociacao();
            return new ResponseEntity<>(regrasAssociacaoDTOList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
