package org.app.paylaod.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.app.annotation.StrongPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationReqPayload {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @StrongPassword(message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

}
