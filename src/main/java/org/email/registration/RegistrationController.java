package org.email.registration;

import lombok.AllArgsConstructor;
import org.email.entity.Registration;
import org.email.service.RegistrationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody Registration request) throws IllegalAccessException {
        return registrationService.registration(request);
    }
}
