package org.email.service;

import lombok.AllArgsConstructor;
import org.email.entity.AppUser;
import org.email.entity.Registration;
import org.email.enums.AppUserRole;
import org.email.registration.EmailValidator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;

    public String registration(Registration request) throws IllegalAccessException {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalAccessException("email not valid ..");
        }
        appUserService.signUpUser(new AppUser(request.getFirstName(),
                request.getLastName()
                , request.getEmail()
                , request.getPassword(),
                AppUserRole.USER));
        return "work in";
    }
}
