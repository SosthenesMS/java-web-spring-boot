package com.agenda.app.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.agenda.app.model.TipoUsuario;
import com.agenda.app.repository.TipoUsuarioRepository;


@RestController
@RequestMapping(value = "/tipo-usuarios")
public class TipoUsuarioController {


    @PostMapping
    public ResponseEntity<Object> criarNovoTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuarioRepository.save(tipoUsuario));
        } catch (DataIntegrityViolationException d) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Tipo de usuário já existente." + d.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar tipo de usuário." + e.getMessage());
        }
    }



    @GetMapping
    public ResponseEntity<List<TipoUsuario>> obterTodosOsTiposDeUsuario(){
        return ResponseEntity.status(HttpStatus.OK).body(tipoUsuarioRepository.findAll());
    }



    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<TipoUsuario>> obterTipoUsuarioPeloId(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(tipoUsuarioRepository.findById(id));
    }



    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletarTipoUsuarioPeloId(@PathVariable("id") int id){
        tipoUsuarioRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tipo de usuário deletado com sucesso!");
    }
    

    @PutMapping
    public ResponseEntity<TipoUsuario> atualizarTipoUsuario(@RequestBody TipoUsuario tipoUsuario){
        return ResponseEntity.status(HttpStatus.OK).body(tipoUsuarioRepository.save(tipoUsuario));
    }


    // @PutMapping
    // public TipoUsuario atualizarTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
    //     TipoUsuario tipoUsuarioBD = tipoUsuarioRepository.findById(tipoUsuario.getIdTipoUsuario()).get();

    //     tipoUsuarioBD.setNome(tipoUsuario.getNome());

    //     return tipoUsuarioRepository.save(tipoUsuarioBD);
    // }



    @GetMapping(value = "/nome/{nome}")
    public TipoUsuario obterTipoUsuarioPeloNome(@PathVariable("nome") String nome) {
        return tipoUsuarioRepository.findByNomeLike(nome + "%");
    }
    

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

}
