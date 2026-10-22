package br.com.github.williiansilva51.linkr.handler.exceptions.user;

public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException(String message) {
        super(message);
    }
}
