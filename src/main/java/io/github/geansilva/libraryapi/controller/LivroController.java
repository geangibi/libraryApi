package io.github.geansilva.libraryapi.controller;

import io.github.geansilva.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.geansilva.libraryapi.controller.dto.ErroResposta;
import io.github.geansilva.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.geansilva.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService service;


    @PostMapping
    public ResponseEntity<Object> Salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            // mapear dto para entidade
            // enviar a entidade para o service validar e salvar na base
            // criar uril para acesso dos dados do livro
            // retonar codigo created com header location
            return ResponseEntity.ok(dto);
        } catch(RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);


        }
    }

}
