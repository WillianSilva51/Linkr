package br.com.github.williiansilva51.linkr.handler.exceptions.domain;

public class DomainAlreadyExistsException extends RuntimeException {
    public DomainAlreadyExistsException(String message) {
        super(message);
    }
}
