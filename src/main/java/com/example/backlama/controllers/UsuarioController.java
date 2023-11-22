package com.example.backlama.controllers;

import com.example.backlama.dto.RegrasAssociacaoDTO;
import com.example.backlama.dto.UsuarioPublicoDTO;
import com.example.backlama.models.*;
import com.example.backlama.services.*;
import com.example.backlama.dto.UsuarioDTO;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Value;
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
    private final ReceitaSeparadaCategoriaService receitaSeparadaCategoriaService;
    private final RegraAssociacaoService regraAssociacaoService;

    @Value("${aplication.foto}")
    private String fotoBase;
    public UsuarioController(UsuarioService usuarioService, EmailService emailService, UsuarioPossuiMaterialService usuarioPossuiMaterialService, ListaPessoalService listaPessoalService,
                             ReceitaService receitaService, ReceitaController receitaController, MaterialService materialService, RegraAssociacaoService regraAssociacaoService,
                             ReceitaUtilizaMaterialService receitaUtilizaMaterialService, ReceitaSeparadaCategoriaService receitaSeparadaCategoriaService ) {
        this.usuarioService = usuarioService;
        this.emailService = emailService;
        this.usuarioPossuiMaterialService = usuarioPossuiMaterialService;
        this.listaPessoalService = listaPessoalService;
        this.receitaService = receitaService;
        this.materialService = materialService;
        this.receitaController = receitaController;
        this.regraAssociacaoService = regraAssociacaoService;
        this.receitaUtilizaMaterialService = receitaUtilizaMaterialService;
        this.receitaSeparadaCategoriaService = receitaSeparadaCategoriaService;
    }

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
        try {
            Usuario registeredUsuario = usuarioService.registerUsuario(usuario);
            registeredUsuario.setFoto(fotoBase);
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
                registeredUsuario.setFoto(fotoBase);
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
    @PutMapping("/material/{id}")
    public ResponseEntity<String> editMaterial(@PathVariable Long id, @RequestBody List<Long> materialListId){
        Usuario usuario = usuarioService.buscarUsuarioById(id);
        if(usuario == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            usuarioPossuiMaterialService.deleteUsuarioPossuiMaterialByUsuarioId(id);
            for (Long idMaterial : materialListId){
                Material material = materialService.buscarMaterialPorId(idMaterial);
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

    @GetMapping("/{id}/material")
    public ResponseEntity<List<Material>> listarMateriaisPessoa(@PathVariable Long id){
        List<Material> materiaisUsuario = new ArrayList<>();
        for (UsuarioPossuiMaterial usuarioPossuiMaterial : usuarioPossuiMaterialService.buscarPorIdUsuario(id)){
            materiaisUsuario.add(usuarioPossuiMaterial.getMaterial());
        }
        try{
            return new ResponseEntity<>(materiaisUsuario, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id_user}/addListapessoal/{id_receita}")
    public ResponseEntity<ListaPessoal> adicionarReceita(@PathVariable Long id_user, @PathVariable Long id_receita){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id_user);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Receita receita = receitaService.buscarReceitaPorId(id_receita);
            ListaPessoal listaPessoal = new ListaPessoal();
            listaPessoal.setUsuario(usuario);
            listaPessoal.setReceita(receita);
            listaPessoal.setProgresso("LISTA_PESSOAL");
            ListaPessoal novaListaPessoal = listaPessoalService.criarListaPessoal(listaPessoal);
            return new ResponseEntity<>(novaListaPessoal, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/listapessoal/remove/{idReceita}")
    public ResponseEntity<String> removeReceita(@PathVariable Long idReceita ){
        try {
            listaPessoalService.deleteListaPessoalByReceitaId(idReceita);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("{id}/listapessoal")
    public ResponseEntity<List<Receita>> listarReceitasListaPessoal(@PathVariable Long id){
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
    @GetMapping("{idUsuario}/listapessoal/{idReceita}")
    public ResponseEntity<ListaPessoal> listarListaPessoal(@PathVariable Long idUsuario, @PathVariable Long idReceita){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(idUsuario);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            ListaPessoal listaPessoal = listaPessoalService.buscarlistaPessoal(idReceita, idUsuario);
            listaPessoal.getUsuario().setSenha(null);
            listaPessoal.getReceita().getUser().setSenha(null);
            return new ResponseEntity<>(listaPessoal, HttpStatus.OK);
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
    @PutMapping("/{id_user}/editListapessoal/{id_receita}/{progresso}")
    public ResponseEntity<ListaPessoal> editarProgresso(@PathVariable Long id_user, @PathVariable Long id_receita, @PathVariable String progresso){
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id_user);
            if(usuario == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            ListaPessoal listaPessoal = listaPessoalService.buscarlistaPessoal(id_receita, id_user);
            listaPessoal.setProgresso(progresso);
            ListaPessoal novaListaPessoal = listaPessoalService.editarListaPessoal(listaPessoal);
            return new ResponseEntity<>(novaListaPessoal, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/minhasreceitas/{id}")
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
    public ResponseEntity<List<Receita>> recomendacoesMateriaisECategorias(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioById(id);
            if (usuario == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Receita> receitasRecomendadas = new ArrayList<>();
            List<ReceitaUtilizaMaterial> todasReceitasUtilizaMaterial = receitaUtilizaMaterialService.listarTodos();
            List<ReceitaSeparadaCategoria> todasReceitaSeparadaCategoria = receitaSeparadaCategoriaService.listAll();

            // Pegar todas as receitas do usuário
            List<Receita> receitasUsuario = receitaService.buscarPorIdUsuario(id);

            // Pegar receitas da lista pessoal
            List<Receita> receitasNaLista = new ArrayList<>();
            List<ListaPessoal> listaPessoal = listaPessoalService.buscarPorIdUsuario(id);
            for (ListaPessoal lista : listaPessoal) {
                receitasNaLista.add(lista.getReceita());
            }
            receitasUsuario.addAll(receitasNaLista);

            // Pegar todos os materiais do usuário
            List<UsuarioPossuiMaterial> meusMateriais = usuarioPossuiMaterialService.buscarPorIdUsuario(id);
            List<Material> materiaisUsuario = new ArrayList<>();
            for (UsuarioPossuiMaterial usuarioPossuiMaterial : meusMateriais) {
                materiaisUsuario.add(usuarioPossuiMaterial.getMaterial());
            }

            // Pegar os materiais das receitas do usuario
            List<ReceitaUtilizaMaterial> receitaUtilizaMaterialList = new ArrayList<>();
            for (Receita receita : receitasUsuario) {
                receitaUtilizaMaterialList.addAll(receitaUtilizaMaterialService.buscarReceitaUtilizaMaterialPorIdReceita(receita.getIdReceita()));
            }
            for (ReceitaUtilizaMaterial receitaUtilizaMaterial : receitaUtilizaMaterialList) {
                materiaisUsuario.add(receitaUtilizaMaterial.getMaterial());
            }

            // Pegar as categorias das receitas do usuario
            List<Categoria> categoriasUsuario = new ArrayList<>();
            List<ReceitaSeparadaCategoria> receitaSeparadaCategoriaList = new ArrayList<>();
            for (Receita receita : receitasUsuario) {
                receitaSeparadaCategoriaList.addAll(receitaSeparadaCategoriaService.buscarReceitaSeparadaCategoriaPorIdReceita(receita.getIdReceita()));
            }
            for (ReceitaSeparadaCategoria receitaSeparadaCategoria : receitaSeparadaCategoriaList) {
                categoriasUsuario.add(receitaSeparadaCategoria.getCategoria());
            }

            // Verificar as regras de associação
            List<RegrasAssociacaoDTO> regrasAssociacaoMateriaisECategorias = regraAssociacaoService.lerRegrasDeAssociacao("python-scripts/regras-materiail-categoria.csv");

            for (RegrasAssociacaoDTO regra : regrasAssociacaoMateriaisECategorias) {
                List<String> antecedents = regra.getAntecedents();
                List<String> consequents = regra.getConsequents();

                // Checar o tipo dos antecedentes
                boolean isMaterialAntecedent = materiaisUsuario.stream().anyMatch(materialUsuario -> antecedents.get(0).contains(materialUsuario.getNome()));
                boolean isCategoriaAntecedent = categoriasUsuario.stream().anyMatch(categoriaUsuario -> antecedents.get(0).contains(categoriaUsuario.getDescricao()));

                // Verificar se os materiais ou categorias do usuário estão nos antecedents da regra
                boolean usuarioTemMateriais = isMaterialAntecedent || (antecedents.size() > 1 && materiaisUsuario.stream().anyMatch(materialUsuario -> antecedents.get(1).contains(materialUsuario.getNome())));
                boolean usuarioTemCategorias = isCategoriaAntecedent || (antecedents.size() > 1 && categoriasUsuario.stream().anyMatch(categoriaUsuario -> antecedents.get(1).contains(categoriaUsuario.getDescricao())));

                // Se os materiais ou categorias do usuário estiverem na regra, adicionar as receitas aos recomendados
                if (usuarioTemMateriais || usuarioTemCategorias) {
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
                        for (ReceitaSeparadaCategoria separadaCategoria : todasReceitaSeparadaCategoria) {
                            Categoria categoriaReceita = separadaCategoria.getCategoria();
                            Receita receita = separadaCategoria.getReceita();
                            // Se a categoria da receita corresponde a um consequent, adicione a receita à lista
                            if (categoriaReceita.getDescricao().contains(consequent)) {
                                if (!receitasRecomendadas.contains(receita)) {
                                    receitasRecomendadas.add(receita);
                                }
                            }
                        }
                    }
                }
            }
            if (!receitasRecomendadas.isEmpty()) {
                receitasRecomendadas.removeAll(receitasNaLista);
                for (Receita receita : receitasRecomendadas) {
                    receita.setUser(null);
                    receita.setFoto(null);
                }
                return new ResponseEntity<>(receitasRecomendadas, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
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