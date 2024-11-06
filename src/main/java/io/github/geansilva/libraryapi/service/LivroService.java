package io.github.geansilva.libraryapi.service;

import io.github.geansilva.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // cria um construtor com os repositórios injetados
public class LivroService {

    private final LivroRepository repository;

}
