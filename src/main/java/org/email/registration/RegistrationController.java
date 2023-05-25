package org.email.registration;

import lombok.AllArgsConstructor;
import org.email.entity.Registration;
import org.email.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody Registration request) throws IllegalAccessException {
        return registrationService.registration(request);
    }
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token")String token)  {
        return registrationService.confirmToken(token);
    }
}
