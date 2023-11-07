package com.example.backlama.controllers;

import com.example.backlama.dto.AvaliacaoDTO;
import com.example.backlama.models.Avaliacao;
import com.example.backlama.models.Receita;
import com.example.backlama.models.Usuario;
import com.example.backlama.services.AvaliacaoService;
import com.example.backlama.services.ReceitaService;
import com.example.backlama.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {
    private final AvaliacaoService avaliacaoService;
    private final UsuarioService usuarioService;
    private final ReceitaService receitaService;

    public AvaliacaoController(AvaliacaoService avaliacaoService, UsuarioService usuarioService, ReceitaService receitaService){
        this.avaliacaoService = avaliacaoService;
        this.usuarioService = usuarioService;
        this.receitaService = receitaService;
    }
    @PostMapping("/create")
    public ResponseEntity<Avaliacao> criarAvaliacao(AvaliacaoDTO avaliacaoDTO){
        try{
            Avaliacao avaliacao = new Avaliacao();
            Usuario usuario = usuarioService.buscarUsuarioById(avaliacaoDTO.getIdUsuario());
            Receita receita = receitaService.buscarReceitaPorId(avaliacaoDTO.getIdReceita());

            avaliacao.setUser(usuario);
            avaliacao.setReceita(receita);
            avaliacao.setEstrelas(avaliacaoDTO.getEstrelas());
            avaliacao.setComentario(avaliacaoDTO.getComentario());
            avaliacao.setTitulo(avaliacaoDTO.getTitulo());

            avaliacaoService.criarAvaliacao(avaliacao);
            return new ResponseEntity<>(avaliacao, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Avaliacao>> listarAvaliacoes(@PathVariable Long id){
        try{
            List<Avaliacao> avaliacoes = avaliacaoService.listarPorIdReceita(id);
            for(Avaliacao avaliacao : avaliacoes){
                avaliacao.getUser().setSenha(null);
            }
            return new ResponseEntity<>(avaliacoes, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
