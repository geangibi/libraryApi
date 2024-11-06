package io.github.geansilva.libraryapi.controller.common;

import io.github.geansilva.libraryapi.controller.dto.ErroCampo;
import io.github.geansilva.libraryapi.controller.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // captura os erros das anotações @NotBlank
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // Informar o tipo de erro
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                    .stream()
                    .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage() )).collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", listaErros);
    }


}
