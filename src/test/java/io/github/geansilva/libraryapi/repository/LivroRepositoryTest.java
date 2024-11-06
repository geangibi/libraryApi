package io.github.geansilva.libraryapi.repository;

import io.github.geansilva.libraryapi.model.Autor;
import io.github.geansilva.libraryapi.model.GeneroLivro;
import io.github.geansilva.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("1234-5678");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("OPERAÇÃO PRATO");
        livro.setDataPublicacao(LocalDate.of(1990, 02, 03));

        Autor autor = autorRepository.findById(UUID.fromString("70b83840-c26e-44bf-b654-6e4802b3c657")).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);


    }


    @Test
    void atualizarLivro(){
        UUID id = UUID.fromString("121763c7-5b78-4264-8bb0-8e5a9dcc2bc0");
         var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("ebc9df1d-34e0-42ab-9a22-b63f730b188b");
        Autor autor  = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletarLivro() {
        UUID id = UUID.fromString("5b5ffc26-4bd9-47a6-a651-b23fc333162c");
        repository.deleteById(id);;
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID ID = UUID.fromString("121763c7-5b78-4264-8bb0-8e5a9dcc2bc0");
        Livro livro = repository.findById(ID).orElse(null);
        System.out.println("Livro");
        System.out.println(livro.getTitulo());

        System.out.println("Autor");
        System.out.println(livro.getAutor().getNome());
    }


    @Test
    void salvarAutorComLivro() {

        Autor autor = new Autor();
        autor.setNome("Gean");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1990, 1, 31));

        Livro livro = new Livro();
        livro.setIsbn("1234-000");
        livro.setPreco(BigDecimal.valueOf(50));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Casa da arvore dos horrores");
        livro.setDataPublicacao(LocalDate.of(1991, 02, 03));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("1234-0001");
        livro2.setPreco(BigDecimal.valueOf(500));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setTitulo("Guia de medicina");
        livro2.setDataPublicacao(LocalDate.of(2015, 02, 03));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        autorRepository.save(autor);
        repository.saveAll(autor.getLivros()); // Para salvar uma lista
    }

    @Test
    void listarLivrosAutor(){
        var id = UUID.fromString("ebc9df1d-34e0-42ab-9a22-b63f730b188b");
        var autor = autorRepository.findById(id).get();

        List<Livro> livrosLista = repository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);


    }

    @Test
    void listarLivrosPorTitulo(){
        List<Livro> lista = repository.findByTitulo("Guia de medicina");
        lista.forEach(System.out::println);
    }

    @Test
    void ListarGenerosAutoresDosLivros() {
        var livros = repository.ListarGeneroAutoresDosLivros();
        livros.forEach(System.out:: println);
    }

    @Test
    void listaPorGeneroPosicionalParamTest() {
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.FICCAO, "preco");
        resultado.forEach(System.out:: println);
    }

    @Test
    void deletePorId() {
        repository.deleteById(UUID.fromString("ab387915-af5a-42e2-943e-ef6e05b342ef"));

    }

}
/*    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("1234-5678");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("OPERAÇÃO PRATO");
        livro.setDataPublicacao(LocalDate.of(1990, 02, 03));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1991, 1, 31));

        livro.setAutor(autor);

        repository.save(livro);


    }*/

