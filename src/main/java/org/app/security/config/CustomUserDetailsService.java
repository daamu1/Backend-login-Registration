package org.app.security.config;

import lombok.RequiredArgsConstructor;
import org.app.enums.UserRole;
import org.app.model.AppUser;
import org.app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Your repository to access user data

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] split = username.split(",");
        String userEmail = split[0];
        UserRole userRole = UserRole.valueOf(split[1]);
        AppUser appUser = userRepository.findByEmailAndUserRole(userEmail, userRole)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new User(appUser.getUsername(), appUser.getPassword(), getAuthorities(appUser));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(AppUser appUser) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + appUser.getUserRole()));
    }

}

