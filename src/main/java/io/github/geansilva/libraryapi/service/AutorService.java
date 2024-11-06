package io.github.geansilva.libraryapi.service;
import io.github.geansilva.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.geansilva.libraryapi.model.Autor;
import io.github.geansilva.libraryapi.model.Livro;
import io.github.geansilva.libraryapi.repository.AutorRepository;
import io.github.geansilva.libraryapi.repository.LivroRepository;
import io.github.geansilva.libraryapi.validador.AutorValidador;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {


    private final AutorRepository repository;
    private final AutorValidador validador;
    private final LivroRepository livroRepository;


    public Autor salvar(Autor autor) {
        validador.validar(autor);
        return repository.save(autor);
    }

    public void Atualizar(Autor autor) {
        if(autor.getId() == null) {
            throw  new IllegalArgumentException("Para atualizar necessário existir um autor cadastrado");
        }
        validador.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id) {
        return repository.findById(id);

    }

    public void deletar(Autor autor) {
        if(possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException("Autor possui livros cadastrados");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade !=null) {
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if (nome != null) {
            return repository.findByNome(nome);
        } else if (nacionalidade != null) {
            return repository.findByNacionalidade(nacionalidade);
        } else {
            return repository.findAll();
        }


    }

    // Pesquisa usando o Example

    public List<Autor> pesquisaByExample(String nome, String nacionalidade) {
        var autor = new Autor(); // cria um autor
        autor.setNome(nome); // seta o autor com o nome recebido
        autor.setNacionalidade(nacionalidade); // seta a nacionalidade

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase() // condições do filtro, exemplo, ignorar caixa alta
                .withIgnoreNullValues() // Ignorar valores nulos
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);

    }

}
