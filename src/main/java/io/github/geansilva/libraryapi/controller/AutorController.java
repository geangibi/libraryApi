package io.github.geansilva.libraryapi.controller;


import io.github.geansilva.libraryapi.controller.dto.AutorDTO;
import io.github.geansilva.libraryapi.controller.dto.ErroResposta;
import io.github.geansilva.libraryapi.controller.mappers.AutorMapper;
import io.github.geansilva.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.geansilva.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.geansilva.libraryapi.model.Autor;
import io.github.geansilva.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
// http://localhost:8080/autores
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) { // O request recebe um JSON (@RequestBody) e cria um instancia do tipo Autor DTO

        Autor autor = mapper.toEntity(dto);
        //  Autor autor = dto.mapearParaAutor(); // Sem Map Structor Com o objeto criado, é possível chamar o metodo mapear para dto que transforma o DTO em um uma instancia da Classe Autor
        service.salvar(autor); // chama o service passando o autor para salvar os dados
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) { // Reber um ID String
        var idAutor = UUID.fromString(id); // insere na variável idAutor a conversão do ID para UUID
        return service   // Retorna chamando o service para recuperar os dados do autor existente
                .obterPorId(idAutor)
                .map(autor -> { // com os dados do Autor itera sobre o mesmo transformando em Entity em um DTO
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto); // Se encontrado retorna ok
                }).orElseGet(() -> ResponseEntity.notFound().build()); // caso contrário retorna notfound


       /* if (autorOptional.isPresent()) { forma manual de mapear um Entity para DTO
            Autor autor = autorOptional.get();
            AutorDTO dto =
                    new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();*/
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO
                ).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarAutores(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {

        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.Atualizar(autor);

        return ResponseEntity.noContent().build();

    }
}
