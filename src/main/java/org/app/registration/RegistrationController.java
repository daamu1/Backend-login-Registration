package org.app.registration;

import lombok.AllArgsConstructor;
import org.app.paylaod.request.Registration;
import org.app.payload.request.RegistrationRequest; // Make sure this import matches your package and class structure
import org.app.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    /**
     * Registers a new user.
     *
     * @param request the registration request
     * @return a response entity with registration status
     */
    @PostMapping
    public ResponseEntity<String> register(@RequestBody Registration request) {
        String response = registrationService.registration(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Confirms a user's email with a token.
     *
     * @param token the confirmation token
     * @return a response entity with confirmation status
     */
    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        String response = registrationService.confirmToken(token);
        return ResponseEntity.ok(response);
    }
}
