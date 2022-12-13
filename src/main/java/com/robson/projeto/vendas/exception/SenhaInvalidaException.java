package com.robson.projeto.vendas.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("Senha Inválida");
    }
}
