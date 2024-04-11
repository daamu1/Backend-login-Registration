package org.app.exception;

public class ConfirmationEmailSentException extends RuntimeException {
    public ConfirmationEmailSentException(String message) {
        super(message);
    }
}
