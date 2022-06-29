package com.example.brenobarbearia.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
        super("inválida");
    }
}
