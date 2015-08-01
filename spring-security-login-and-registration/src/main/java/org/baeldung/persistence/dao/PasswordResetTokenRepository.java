package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.PasswordResetToken;
import org.baeldung.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

}
