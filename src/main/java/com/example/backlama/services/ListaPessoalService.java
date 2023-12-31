package com.example.backlama.services;

import com.example.backlama.models.ListaPessoal;
import com.example.backlama.repositories.ListaPessoalRepository;
import jakarta.transaction.Transactional;
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
    public ListaPessoal buscarlistaPessoal(Long idReceita, Long idUsuario){
        return  listaPessoalRepository.findByReceita_IdReceitaAndUsuario_IdUsuario(idReceita, idUsuario);
    }
    public ListaPessoal criarListaPessoal(ListaPessoal listaPessoal){
        return listaPessoalRepository.save(listaPessoal);
    }
    @Transactional
    public void deleteListaPessoalByUsuarioId(Long idUsuario){
        listaPessoalRepository.deleteByUsuario_IdUsuario(idUsuario);
    }
    @Transactional
    public void deleteListaPessoalByReceitaId(Long idReceita){
        listaPessoalRepository.deleteByReceita_IdReceita(idReceita);
    }
    public ListaPessoal editarListaPessoal(ListaPessoal novaListaPessoal){
        ListaPessoal listaPessoal = listaPessoalRepository.findByIdListaPessoal(novaListaPessoal.getIdListaPessoal());
        if(listaPessoal != null){
            novaListaPessoal.setIdListaPessoal(listaPessoal.getIdListaPessoal());
            return listaPessoalRepository.save(novaListaPessoal);
        }else{
            return null;
        }
    }
}