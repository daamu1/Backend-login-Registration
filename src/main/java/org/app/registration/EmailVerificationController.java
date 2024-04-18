package org.app.registration;

import lombok.RequiredArgsConstructor;
import org.app.service.imp.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmailVerificationController {
    private final RegistrationService registrationService;

    /**
     * Confirms a user's email with a token.
     *
     * @param token the confirmation token
     * @return a response entity with confirmation status
     */
    @GetMapping("/confirm")
    public String confirmEmailVerification(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
