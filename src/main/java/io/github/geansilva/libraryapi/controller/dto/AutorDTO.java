package io.github.geansilva.libraryapi.controller.dto;

import io.github.geansilva.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank (message = "Campo não pode ser nulo")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrão")
        String nome,
        @NotNull (message = "Campo não pode ser nulo")
        @Past(message = "campo obrigatorio")
        LocalDate dataNascimento,
        @NotBlank (message = "Campo não pode ser nulo")
        @Size(min = 2, max = 50, message = "campo fora do tamanho padrão")
        String nacionalidade) {

    public Autor mapearParaAutor() {
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}

