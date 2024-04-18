package org.app.registration;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.app.paylaod.request.AuthenticationReqDTO;
import org.app.paylaod.request.RegistrationReqPayload;
import org.app.paylaod.response.AuthenticationResDTO;
import org.app.paylaod.response.RegistrationResPayload;
import org.app.service.IRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {
    private final IRegistrationService registrationService;

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
     * Authenticate a user and generate tokens.
     *
     * @param request the authentication request
     * @return a response entity with authentication and token details
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResDTO> authenticate(@RequestBody @Valid AuthenticationReqDTO request) {
        return ResponseEntity.ok(
                registrationService.authenticateUser(request));
    }

//    @GetMapping("/authenticate")
//    public ResponseEntity<String> authenticate() {
//        return ResponseEntity.ok(
//                "Hello bhosdike");
//    }

}
