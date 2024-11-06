package io.github.geansilva.libraryapi.repository;

import io.github.geansilva.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransacoesTest {
    @Autowired
    private TransacaoService transacaoService;

    @Test
    void atualizacaoSemAtualizar() {
        transacaoService.atualizacaoSemAtualizar();

    }
}
