package org.app.paylaod.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.app.annotation.StrongPassword;
import org.app.enums.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @StrongPassword()
    private String password;

    @NotNull(message = "Role is required")
    private UserRole role;

}
