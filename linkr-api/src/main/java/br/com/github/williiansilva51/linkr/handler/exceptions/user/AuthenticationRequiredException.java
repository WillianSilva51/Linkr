package br.com.github.williiansilva51.linkr.handler.exceptions.user;

public class AuthenticationRequiredException extends RuntimeException {
    public AuthenticationRequiredException(String message) {
        super(message);
    }
}
