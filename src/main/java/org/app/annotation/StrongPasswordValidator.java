package org.app.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            buildConstraintViolationWithTemplate(context, "Password cannot be null.");
            return false;
        }

        // Password must be at least 8 characters long
        if (value.length() < 8) {
            buildConstraintViolationWithTemplate(context, "Password must be at least 8 characters long.");
            return false;
        }

        // Check for at least one uppercase letter
        if (!value.matches(".*[A-Z].*")) {
            buildConstraintViolationWithTemplate(context, "Password must contain at least one uppercase letter.");
            return false;
        }

        // Check for at least one lowercase letter
        if (!value.matches(".*[a-z].*")) {
            buildConstraintViolationWithTemplate(context, "Password must contain at least one lowercase letter.");
            return false;
        }

        // Check for at least one digit
        if (!value.matches(".*\\d.*")) {
            buildConstraintViolationWithTemplate(context, "Password must contain at least one digit.");
            return false;
        }

        // Check for at least one special character
        if (!value.matches(".*[!@#$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>/?].*")) {
            buildConstraintViolationWithTemplate(context, "Password must contain at least one special character.");
            return false;
        }

        // If none of the conditions are met, the password is valid
        return true;
    }

    private void buildConstraintViolationWithTemplate(ConstraintValidatorContext context, String template) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template)
                .addConstraintViolation();
    }
}
