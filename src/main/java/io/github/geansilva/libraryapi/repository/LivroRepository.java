package io.github.geansilva.libraryapi.repository;

import io.github.geansilva.libraryapi.model.Autor;
import io.github.geansilva.libraryapi.model.GeneroLivro;
import io.github.geansilva.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    boolean existsByAutor(Autor autor);

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    @Query("""
            select l.genero
                from Livro l
                join l.autor a 
                where a.nacionalidade = "Brasileira"
                order by l.genero
            
            """)
    List<String> ListarGeneroAutoresDosLivros();

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao ")
    List<Livro> findByGenero(
            @Param("genero")GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade
            );

    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String preco);

    @Modifying
    @Transactional
    @Query(" delete from Livro where id = ?1")
    void deleteById(UUID id);
}
