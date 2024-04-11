package org.app.service;

import lombok.AllArgsConstructor;
import org.app.enums.UserRole;
import org.app.model.AppUser;
import org.app.paylaod.request.RegistrationReqPayload;
import org.app.service.imp.EmailSender;
import org.app.model.ConfirmationToken;
import org.app.utils.EmailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private final EmailSender emailSender;
private ConfirmationTokenService confirmationTokenService;
    public String registration(RegistrationReqPayload request)  {

       String token= appUserService.signUpUser(new AppUser(request.getFirstName(),
                request.getLastName()
                , request.getEmail()
                , request.getPassword(),
                UserRole.USER));
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                EmailMessage. buildEmail(request.getFirstName(), link));

        return "sdf";
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(
                confirmationToken.getAppUser().getEmail());
        return "confirmed";
    }


}