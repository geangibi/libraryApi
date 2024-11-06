package io.github.geansilva.libraryapi.validador;

import io.github.geansilva.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.geansilva.libraryapi.model.Autor;
import io.github.geansilva.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Component
@Repository
public class AutorValidador {

    private AutorRepository repository;


    public AutorValidador(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if(existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if(autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
