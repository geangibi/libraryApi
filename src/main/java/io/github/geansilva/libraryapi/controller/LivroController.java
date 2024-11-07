package io.github.geansilva.libraryapi.controller;

import io.github.geansilva.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.geansilva.libraryapi.controller.dto.ErroResposta;
import io.github.geansilva.libraryapi.controller.mappers.LivroMapper;
import io.github.geansilva.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.geansilva.libraryapi.model.Livro;
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
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Object> Salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {
            Livro livro = mapper.toEntity(dto);
            service.salvar(livro);
            var url = gerarHeaderLocation(livro.getID());
            // retonar codigo created com header location
            return ResponseEntity.created(url).build();
        } catch(RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);


        }
    }

}
