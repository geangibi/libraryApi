package io.github.geansilva.libraryapi.repository;

import io.github.geansilva.libraryapi.model.Autor;
import io.github.geansilva.libraryapi.repository.AutorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.temporal.TemporalQueries.localDate;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1991, 1, 31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo:" + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("1145b9fd-eb35-4329-afe4-1063f80fb23b");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1990, 2, 28));

            repository.save(autorEncontrado);
        }



    }

    @Test
    public void listarTodos() {
        List<Autor> autorList = repository.findAll();
        autorList.forEach(System.out::println);
    }

    @Test
    public void countTeste() {
        System.out.println("Total de registros: " + repository.count());
    }

    @Test
    public void deletePorIdTest() {
        var id = UUID.fromString("1145b9fd-eb35-4329-afe4-1063f80fb23b");
        repository.deleteById(id);
    }

}
