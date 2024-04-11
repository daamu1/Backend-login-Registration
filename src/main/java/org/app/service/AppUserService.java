package org.app.service;

import lombok.AllArgsConstructor;
import org.app.constant.Constant;
import org.app.model.AppUser;
import org.app.repository.UserRepository;
import org.app.model.ConfirmationToken;
import org.app.repository.ConfirmationTokenRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(Constant.USER_NOT_FOUND_MSG)));
    }

    public String signUpUser(AppUser appUser) throws IllegalAccessException {
        boolean userExist = userRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExist) {
            //TODO check of  attributes are same anf
            //TODO  if email not confirmed send send confirm  email
            throw new IllegalAccessException("oop's sorry email already taken");
        }
        String encodePassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodePassword);
        userRepository.save(appUser);
        //TODO  Send confirmation token
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);
        confirmationTokenRepository.save(confirmationToken);
        //TODO Send email
        return token;
    }
    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }
}
