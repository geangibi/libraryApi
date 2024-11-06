package io.github.geansilva.libraryapi.service;

import io.github.geansilva.libraryapi.repository.AutorRepository;
import io.github.geansilva.libraryapi.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualizacaoSemAtualizar() {
        var livro = livroRepository.findById(UUID.fromString("121763c7-5b78-4264-8bb0-8e5a9dcc2bc0")).orElse(null);

        livro.setTitulo("Operação Prato");
    }
}
