package io.github.geansilva.libraryapi.controller.mappers;

import io.github.geansilva.libraryapi.controller.dto.AutorDTO;
import io.github.geansilva.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // transforma em um componente para poder se injetado
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDTO dto); // transforma o DTO em Entity

    AutorDTO toDTO(Autor autor); // Transforma Entity em DTO

}
