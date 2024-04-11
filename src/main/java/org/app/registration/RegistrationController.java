package org.app.registration;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.app.paylaod.request.RegistrationReqPayload;
import org.app.paylaod.response.RegistrationResPayload;
import org.app.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    /**
     * Registers a new user.
     *
     * @param request the registration request
     * @return a response entity with registration status
     */
    @PostMapping("/registration")
    public ResponseEntity<RegistrationResPayload> register(@RequestBody @Valid RegistrationReqPayload request) {
        return ResponseEntity.ok(registrationService.registration(request));
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
