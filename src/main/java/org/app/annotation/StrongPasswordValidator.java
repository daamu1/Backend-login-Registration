package org.app.annotation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        // Password must be at least 8 characters long
        if (value.length() < 8) {
            return false;
        }

        // Check for at least one uppercase letter
        if (!value.matches(".*[A-Z].*")) {
            return false;
        }

        // Check for at least one lowercase letter
        if (!value.matches(".*[a-z].*")) {
            return false;
        }

        // Check for at least one digit
        if (!value.matches(".*\\d.*")) {
            return false;
        }

        // Check for at least one special character
        if (!value.matches(".*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?].*")) {
            return false;
        }

        return true;
    }
}
