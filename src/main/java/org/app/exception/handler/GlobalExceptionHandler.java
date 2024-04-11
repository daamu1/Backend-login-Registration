package org.app.exception.handler;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.app.exception.ConfirmationEmailSentException;
import org.app.exception.EmailAlreadyUsed;
import org.app.exception.InvalidToken;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyUsed.class)
    public ResponseEntity<String> handleEmailAlreadyUsed(EmailAlreadyUsed emailAlreadyUsed) {
        return new ResponseEntity<>(emailAlreadyUsed.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationError(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> errorMessages = methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidToken.class)
    public ResponseEntity<String> handleInvalidToken(InvalidToken invalidToken) {
        return new ResponseEntity<>(invalidToken.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormatException(InvalidFormatException ex) {
        String fieldName = ex.getPath().stream()
                .reduce((first, second) -> second)
                .map(JsonMappingException.Reference::getFieldName)
                .orElse("unknown field");

        String expectedValues = Arrays.stream(ex.getTargetType().getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Invalid value for field");
        body.put("field", fieldName);
        body.put("expected", expectedValues);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailSendException.class)
    public ResponseEntity<String> handleMailSendException(MailSendException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ConfirmationEmailSentException.class)
    public ResponseEntity<String> handleConfirmationEmailSentException(ConfirmationEmailSentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }

}
