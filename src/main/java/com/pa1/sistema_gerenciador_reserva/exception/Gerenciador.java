package com.pa1.sistema_gerenciador_reserva.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class Gerenciador {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacaoCampos(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            erros.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> tratarDuplicidadeBanco(DataIntegrityViolationException ex) {
        Map<String, String> erro = new HashMap<>();
        String msg = ex.getMostSpecificCause().getMessage();

        if (msg.contains("cpf")) {
            erro.put("cpf", "Este CPF já está em uso.");
        } else if (msg.contains("email")) {
            erro.put("email", "Este e-mail já está cadastrado.");
        } else {
            erro.put("erro", "Integridade de dados violada");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> tratarErroLogin(BadCredentialsException ex) {
        Map<String, String> erro = new HashMap<>();

        erro.put("mensagem", "E-mail ou senha incorretos. Verifique seus dados e tente novamente.");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Map<String, String>> tratarUsuarioDesativado(DisabledException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", "Sua conta está desativada. Entre em contato com o síndico.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> tratarResponseStatus(ResponseStatusException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("mensagem", ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(erro);
    }
}





