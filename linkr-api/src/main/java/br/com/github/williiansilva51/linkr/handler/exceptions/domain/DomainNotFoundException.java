package br.com.github.williiansilva51.linkr.handler.exceptions.domain;

public class DomainNotFoundException extends RuntimeException {
    public DomainNotFoundException(String message) {
        super(message);
    }
}
