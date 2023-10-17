package com.example.backlama.services;

import com.example.backlama.models.ListaPessoal;
import com.example.backlama.repositories.ListaPessoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaPessoalService {
    private final ListaPessoalRepository listaPessoalRepository;
    public ListaPessoalService(ListaPessoalRepository listaPessoalRepository){
        this.listaPessoalRepository = listaPessoalRepository;
    }
    public List<ListaPessoal> buscarPorIdUsuario(Long idUsuario){
        return listaPessoalRepository.findByUsuario_IdUsuario(idUsuario);
    }
    public void criarListaPessoal(ListaPessoal listaPessoal){
        listaPessoalRepository.save(listaPessoal);
    }
    public void deleteListaPessoalByUsuarioId(Long idUsuario){
        listaPessoalRepository.deleteByUsuario_IdUsuario(idUsuario);
    }
}