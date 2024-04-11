package org.app.service;

import lombok.AllArgsConstructor;
import org.app.enums.UserRole;
import org.app.exception.ConfirmationEmailSentException;
import org.app.exception.EmailAlreadyUsed;
import org.app.exception.InvalidToken;
import org.app.model.AppUser;
import org.app.paylaod.request.RegistrationReqPayload;
import org.app.paylaod.response.RegistrationResPayload;
import org.app.repository.ConfirmationTokenRepository;
import org.app.repository.UserRepository;
import org.app.service.imp.EmailSender;
import org.app.model.ConfirmationToken;
import org.app.utils.DateTimeUtils;
import org.app.utils.EmailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final EmailSender emailSender;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public RegistrationResPayload registration(RegistrationReqPayload request) {
        Optional<AppUser> existingUser = userRepository.findByEmail(request.getEmail());
        String token = UUID.randomUUID().toString();

        if (existingUser.isPresent()) {
            AppUser user = existingUser.get();
            if (user.isEmailVerified()) {
                throw new EmailAlreadyUsed("Oops, sorry, email already taken.");
            } else {
                ConfirmationToken confirmationToken = confirmationTokenRepository.findByAppUser(user);
                confirmationToken.setToken(token);
                confirmationToken.setCreatedAt(System.currentTimeMillis());
                confirmationToken.setExpiresAt(System.currentTimeMillis() + DateTimeUtils.minutesToMilliseconds(15));
                confirmationTokenRepository.save(confirmationToken);
                EmailMessage.sendConfirmationEmail(emailSender, user.getEmail(), user.getFirstName(), token);
                throw new ConfirmationEmailSentException("Confirmation email resent to your email.");
            }
        }
        AppUser appUser = new AppUser(request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                bCryptPasswordEncoder.encode(request.getPassword()),
                UserRole.USER);
        appUser.setUserRole(request.getRole());
        appUser.setRegisteredAt(System.currentTimeMillis());
        userRepository.save(appUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                System.currentTimeMillis(),
                System.currentTimeMillis() + DateTimeUtils.minutesToMilliseconds(15),
                appUser
        );
        confirmationTokenRepository.save(confirmationToken);
        EmailMessage.sendConfirmationEmail(emailSender, appUser.getEmail(), appUser.getFirstName(), token);


        return RegistrationResPayload
                .builder()
                .name(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail())
                .userRole(request.getRole())
                .registeredAt(DateTimeUtils.epochToLocalDateTime(appUser.getRegisteredAt()))
                .build();
    }


    @Transactional
    public String confirmToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .map(confirmationToken -> {
                    LocalDateTime expiredAt = DateTimeUtils.epochToLocalDateTime(confirmationToken.getExpiresAt());
                    if (LocalDateTime.now().isAfter(expiredAt)) {
                        return "link-expire";
                    } else if (confirmationToken.getConfirmedAt() != null) {
                        return "already_confirmed";
                    } else {
                        confirmationToken.setConfirmedAt(System.currentTimeMillis());
                        confirmationToken.getAppUser().setEmailVerified(true);
                        confirmationTokenRepository.save(confirmationToken);
                        return "success";
                    }
                })
                .orElse("invalid-token");
    }
}