package io.github.geansilva.libraryapi.controller.mappers;

import io.github.geansilva.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.geansilva.libraryapi.model.Livro;
import io.github.geansilva.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

/*
Para mapear o CadastroLivroDTO em uma Entidade é necessário usar uma classe abstrata, isso ocorre, pois a classe
Livro possui um um atributo do tipo Autor, para isso precisamos identificar o autor na base de dados para posteriormente
mapear e conseguir realizar o cadastro, para isso é utilizado as classes e métodos abstratos.

Exemplo:
    target: campo o qual deseja mapear
    expression: executa uma instrução Java chamando o metodo findByAutor do autorrepository passando o autor
    @Mapping(target = "autor", expression = "java(autorRepository.findById(idAutor()).orElse(null)")


*/

@Mapper(componentModel = "spring")
public abstract class LivroMapper {

    @Autowired
    protected AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);


}
