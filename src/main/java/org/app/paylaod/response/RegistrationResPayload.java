package org.app.paylaod.response;

import lombok.Builder;
import lombok.Data;
import org.app.enums.UserRole;

import java.time.LocalDateTime;

@Data
@Builder
public class RegistrationResPayload {
    private String name;
    private UserRole userRole;
    private LocalDateTime registeredAt;
    private String email;
}
