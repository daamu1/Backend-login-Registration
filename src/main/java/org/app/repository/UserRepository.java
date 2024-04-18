package org.app.repository;

import org.app.enums.UserRole;
import org.app.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmailAndUserRole(String email, UserRole userRole);

//    @Transactional
//    @Modifying
//    @Query("UPDATE AppUser a " +
//            "SET a.enabled = TRUE WHERE a.email = ?1")
//    void enableAppUser(String email);

    Optional<AppUser> findByEmailAndEmailVerified(String email, boolean isEmailVerified);

    Optional<AppUser> findByEmail(String email);
}
