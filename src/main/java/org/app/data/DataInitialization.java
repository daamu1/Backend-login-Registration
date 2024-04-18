package org.app.data;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.app.enums.UserRole;
import org.app.model.AppUser;
import org.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DataInitialization {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAdminUser() {
        boolean adminExists = userRepository.findByEmail("saurabhmaithani01921@gmail.com").isPresent();
        if (!adminExists) {
            AppUser adminUser = new AppUser("Saurabh", "Maithani", "saurabhmaithani01921@gmail.com", passwordEncoder.encode("Winter221"), UserRole.ADMIN);
            adminUser.setRegisteredAt(System.currentTimeMillis());
            adminUser.setEmailVerified(true);
            userRepository.save(adminUser);
        }
    }

}
