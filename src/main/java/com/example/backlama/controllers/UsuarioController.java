package com.example.backlama.controllers;

import com.example.backlama.dto.RegrasAssociacaoDTO;
import com.example.backlama.dto.UsuarioPublicoDTO;
import com.example.backlama.models.*;
import com.example.backlama.services.*;
import com.example.backlama.dto.UsuarioDTO;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@ComponentScan(basePackageClasses = UsuarioService.class)
@RequestMapping("/user")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final UsuarioPossuiMaterialService usuarioPossuiMaterialService;
    private final ListaPessoalService listaPessoalService;
    private final ReceitaService receitaService;
    private final ReceitaController receitaController;
    private final MaterialService materialService;
    private final ReceitaUtilizaMaterialService receitaUtilizaMaterialService;
    private final RegraAssociacaoService regraAssociacaoService;

    public UsuarioController(UsuarioService usuarioService, EmailService emailService, UsuarioPossuiMaterialService usuarioPossuiMaterialService, ListaPessoalService listaPessoalService,
                             ReceitaService receitaService, ReceitaController receitaController, MaterialService materialService, RegraAssociacaoService regraAssociacaoService, ReceitaUtilizaMaterialService receitaUtilizaMaterialService) {
        this.usuarioService = usuarioService;
        this.emailService = emailService;
        this.usuarioPossuiMaterialService = usuarioPossuiMaterialService;
        this.listaPessoalService = listaPessoalService;
        this.receitaService = receitaService;
        this.materialService = materialService;
        this.receitaController = receitaController;
        this.regraAssociacaoService = regraAssociacaoService;
        this.receitaUtilizaMaterialService = receitaUtilizaMaterialService;
    }

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
        try {
            Usuario registeredUsuario = usuarioService.registerUsuario(usuario);
            //registeredUsuario.setFoto("foto_base");
            emailService.sendConfirmationEmail(registeredUsuario);
            return new ResponseEntity<>(registeredUsuario, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/register/all")
    @PermitAll
    public ResponseEntity<List<Usuario>> registerUsers(@RequestBody List<Usuario> usuarios) {
        try {
            List<Usuario> registeredUsuarios = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                Usuario registeredUsuario = usuarioService.registerUsuario(usuario);
                //registeredUsuario.setFoto("foto_base");
                registeredUsuarios.add(registeredUsuario);
            }

            return new ResponseEntity<>(registeredUsuarios, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<Map<String, Object>> loginUsuario(@RequestBody Usuario usuario) {
        try {
            Map<String, Object> response = usuarioService.loginUsuario(usuario.getEmail(), usuario.getSenha());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/confirm")
    @PermitAll
    public ResponseEntity<String> confirmRegistration(@RequestParam("email") String email) {
        try {
            boolean confirmed = usuarioService.confirmRegistration(email);
            if (confirmed) {
                return new ResponseEntity<>("Registro confirmado com sucesso!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Falha ao confirmar o registro. Token inválido ou expirado.", HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pessoal/{id}")
    public ResponseEntity<UsuarioDTO> visualizarPerfilPessoal(@PathVariable Long id){
        //Set Usuario
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Usuario usuario = usuarioService.buscarUsuarioById(id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            usuario.setSenha(null);
            usuario.setAdmin(false);
            usuarioDTO.setUsuario(usuario);
            //Set Materiais
            List<Material> materialList = new ArrayList<>();
            for (UsuarioPossuiMaterial possuiMaterial : usuarioPossuiMaterialService.buscarPorIdUsuario(usuario.getIdUsuario())) {
                Material material = possuiMaterial.getMaterial();
                materialList.add(material);
            }
            usuarioDTO.setMateriais(materialList);
            //Set ListaPessoal
            List<Receita> listaPessoal = new ArrayList<>();
            for (ListaPessoal pessoalList : listaPessoalService.buscarPorIdUsuario(usuario.getIdUsuario())) {
                Receita receita = pessoalList.getReceita();
                listaPessoal.add(receita);
            }
            usuarioDTO.setListaPessoal(listaPessoal);
            //Set Receitas Pessoais
            usuarioDTO.setReceitaPessoal(receitaService.buscarPorIdUsuario(usuario.getIdUsuario()));
            return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPublicoDTO> visualizarPessoa(@PathVariable Long id){
        try{
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            if (usuario == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Receita> receitasPublicas = receitaService.buscarReceitasPublicas();
            UsuarioPublicoDTO usuarioPublicoDTO = new UsuarioPublicoDTO();
            usuario.setSenha(null);
            usuarioPublicoDTO.setUsuario(usuario);
            for (Receita receita : receitasPublicas){
                receita.getUser().setSenha(null);
            }
            usuarioPublicoDTO.setReceitasPublicas(receitasPublicas);
            return new ResponseEntity<>(usuarioPublicoDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/editar/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario updatedUsuario) {
        Usuario usuario = usuarioService.buscarUsuarioById(id);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            if((updatedUsuario.getNome() != null) && !(updatedUsuario.getNome().isEmpty())) {
                usuario.setNome(updatedUsuario.getNome());}
            if(updatedUsuario.getApelido() != null && !(updatedUsuario.getApelido().isEmpty())){
                usuario.setApelido(updatedUsuario.getApelido());}
            if(updatedUsuario.getEmail() != null && !(updatedUsuario.getEmail().isEmpty())){
                usuario.setEmail(updatedUsuario.getEmail());}
            if(updatedUsuario.getSenha() != null && !(updatedUsuario.getSenha().isEmpty())) {
                usuario.setSenha(updatedUsuario.getSenha());}
            if(updatedUsuario.getNivelExperiencia() != null && !(updatedUsuario.getNivelExperiencia().isEmpty())) {
                usuario.setNivelExperiencia(updatedUsuario.getNivelExperiencia());}
            if(updatedUsuario.getFoto() != null && !(updatedUsuario.getFoto().isEmpty())) {
                usuario.setFoto(updatedUsuario.getFoto());}

            Usuario updated = usuarioService.updateUsuario(usuario);
            updated.setSenha(null);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/material/{id}")
    public ResponseEntity<String> addMaterial(@PathVariable Long id, @RequestBody List<Long> materialIdList){
        Usuario usuario = usuarioService.buscarUsuarioById(id);
        if(usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            List<Material> addMaterials = new ArrayList<>();
            for (Long idMaterial : materialIdList) {
                Material material = materialService.buscarMaterialPorId(idMaterial);
                addMaterials.add(material);
            }
            for (Material material : addMaterials) {
                UsuarioPossuiMaterial usuarioPossuiMaterial = new UsuarioPossuiMaterial();
                usuarioPossuiMaterial.setMaterial(material);
                usuarioPossuiMaterial.setUsuario(usuario);
                usuarioPossuiMaterialService.criarUsuarioPossuiMaterial(usuarioPossuiMaterial);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id_user}/listapessoal/{id_receita}/{progresso}")
    public ResponseEntity<ListaPessoal> adicionarReceita(@PathVariable Long id_user, @PathVariable Long id_receita, @PathVariable String progresso){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id_user);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Receita receita = receitaService.buscarReceitaPorId(id_receita);
            ListaPessoal listaPessoal = new ListaPessoal();
            listaPessoal.setUsuario(usuario);
            listaPessoal.setReceita(receita);
            listaPessoal.setProgresso(progresso);
            ListaPessoal novaListaPessoal = listaPessoalService.criarListaPessoal(listaPessoal);
            return new ResponseEntity<>(novaListaPessoal, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/listapessoal/remove/{id}")
    public ResponseEntity<String> removeReceita(@PathVariable Long id, @RequestBody List<Long> receitaIdList){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<Receita> removeReceitas = new ArrayList<>();
            for (Long idReceita : receitaIdList) {
                Receita receita = receitaService.buscarReceitaPorId(idReceita);
                removeReceitas.add(receita);
            }
            for (Receita receita : removeReceitas) {
                listaPessoalService.deleteListaPessoalByReceitaId(receita.getIdReceita());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("{id}/listapessoal")
    public ResponseEntity<List<Receita>> listarListaPessoal(@PathVariable Long id){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Receita> receitaList = new ArrayList<>();
            List<ListaPessoal> listaPessoalList = listaPessoalService.buscarPorIdUsuario(id);
            for (ListaPessoal listaPessoal: listaPessoalList) {
                    receitaList.add(listaPessoal.getReceita());
            }
            return new ResponseEntity<>(receitaList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}/listapessoal/andamento")
    public ResponseEntity<List<Receita>> listarEmAndamento(@PathVariable Long id){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Receita> receitaList = new ArrayList<>();
            List<ListaPessoal> listaPessoalList = listaPessoalService.buscarPorIdUsuario(id);
            for (ListaPessoal listaPessoal: listaPessoalList) {
                if(listaPessoal.getProgresso().equals("EM_ANDAMENTO")) {
                    receitaList.add(listaPessoal.getReceita());
                }
            }
            return new ResponseEntity<>(receitaList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id_user}/listapessoal/{id_receita}/{progresso}")
    public ResponseEntity<ListaPessoal> editarProgresso(@PathVariable Long id_user, @PathVariable Long id_receita, @PathVariable String progresso){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id_user);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            ListaPessoal listaPessoal = listaPessoalService.buscarPorIdReceita(id_receita);
            listaPessoal.setProgresso(progresso);
            ListaPessoal novaListaPessoal = listaPessoalService.editarListaPessoal(listaPessoal);
            return new ResponseEntity<>(novaListaPessoal, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/minhasreceitas/{id}")
    public ResponseEntity<List<Receita>> visualizarReceitas(@PathVariable Long id){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Receita> minhasReceitas = receitaService.buscarPorIdUsuario(usuario.getIdUsuario());
            return new ResponseEntity<>(minhasReceitas, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}/apriori")
    public ResponseEntity<List<Receita>> recomendacoes(@PathVariable Long id){
        try{
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Receita> receitasRecomendadas = new ArrayList<>();
            List<ReceitaUtilizaMaterial> todasReceitasUtilizaMaterial = receitaUtilizaMaterialService.listarTodos();

            //Pegar todas as receitas do usuário
            List<Receita> receitasUsuario = receitaService.buscarPorIdUsuario(id);

            //Pegar receitas da lista pessoal
            List<Receita> receitasNaLista = new ArrayList<>();
            List<ListaPessoal> listaPessoal = listaPessoalService.buscarPorIdUsuario(id);
            for(ListaPessoal lista : listaPessoal){
                receitasNaLista.add(lista.getReceita());
            }
            receitasUsuario.addAll(receitasNaLista);

            //Pegar todos os materiais do usuário
            List<UsuarioPossuiMaterial> meusMateriais = usuarioPossuiMaterialService.buscarPorIdUsuario(id);
            List<Material> materiaisUsuario = new ArrayList<>();
            for(UsuarioPossuiMaterial usuarioPossuiMaterial : meusMateriais){
                materiaisUsuario.add(usuarioPossuiMaterial.getMaterial());
            }

            //Pegar os materiais das receitas do usuario
            List<ReceitaUtilizaMaterial> receitaUtilizaMaterialList = new ArrayList<>();
            for(Receita receita : receitasUsuario){
                receitaUtilizaMaterialList.addAll(receitaUtilizaMaterialService.buscarReceitaUtilizaMaterialPorIdReceita(receita.getIdReceita()));
            }
            for(ReceitaUtilizaMaterial receitaUtilizaMaterial : receitaUtilizaMaterialList){
                materiaisUsuario.add(receitaUtilizaMaterial.getMaterial());
            }

            //Verificar as regras de associação
            List<RegrasAssociacaoDTO> regrasAssociacao = regraAssociacaoService.lerRegrasDeAssociacao();

            for (RegrasAssociacaoDTO regra : regrasAssociacao) {
                List<String> antecedents = regra.getAntecedents();
                List<String> consequents = regra.getConsequents();

                // Verificar se os materiais do usuário estão no antecedents da regra
                boolean usuarioTemMateriais = materiaisUsuario.stream().anyMatch(materialUsuario -> antecedents.stream().anyMatch(antecedent -> materialUsuario.getNome().contains(antecedent)));

                // Se os materiais do usuário estiverem na regra, adicionar as receitas aos recomendados
                if (usuarioTemMateriais) {
                    for (String consequent : consequents) {
                        for (ReceitaUtilizaMaterial utilizaMaterial : todasReceitasUtilizaMaterial) {
                            Material materialReceita = utilizaMaterial.getMaterial();
                            Receita receita = utilizaMaterial.getReceita();
                            // Se o material da receita corresponde a um consequent, adicione a receita à lista
                            if (materialReceita.getNome().contains(consequent)) {
                                if (!receitasRecomendadas.contains(receita)) {
                                    receitasRecomendadas.add(receita);
                                }
                            }
                        }
                    }
                }
            }
            if (!receitasRecomendadas.isEmpty()){
                receitasRecomendadas.removeAll(receitasNaLista);
                for(Receita receita : receitasRecomendadas){
                    receita.setUser(null);
                    receita.setFoto(null);
                }
                return new ResponseEntity<>(receitasRecomendadas, HttpStatus.OK);
            }
            receitasRecomendadas.addAll(receitaService.listarTodasReceitas());
            receitasRecomendadas.removeAll(receitasNaLista);
            for(Receita receita : receitasRecomendadas){
                receita.setUser(null);
                receita.setFoto(null);
            }
            return new ResponseEntity<>(receitasRecomendadas, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/listartodos")
    public ResponseEntity<List<Usuario>> listarTodos(){
        try{
            List<Usuario> todosUsuarios = usuarioService.listarTodos();
            return new ResponseEntity<>(todosUsuarios, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            usuarioPossuiMaterialService.deleteUsuarioPossuiMaterialByUsuarioId(usuario.getIdUsuario());
            listaPessoalService.deleteListaPessoalByUsuarioId(usuario.getIdUsuario());
            for (Receita receita : receitaService.buscarPorIdUsuario(usuario.getIdUsuario())){
                receitaController.deleteReceita(receita.getIdReceita());
            }
            emailService.sendDeleteEmail(usuario);
            usuarioService.deleteUsuario(usuario);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}