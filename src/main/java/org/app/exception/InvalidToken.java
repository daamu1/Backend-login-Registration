package org.app.exception;

public class InvalidToken extends RuntimeException {
    public InvalidToken(String tokenNotFound) {
        super(tokenNotFound);
    }
}
