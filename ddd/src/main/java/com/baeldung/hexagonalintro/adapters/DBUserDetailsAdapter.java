package com.baeldung.hexagonalintro.adapters;

import com.baeldung.hexagonalintro.ports.UserDetailsPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;

@Primary
@Repository
class DBUserDetailsAdapter implements UserDetailsPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean isCorrectPassword(String username, String newPassword) {
        UserDetails userDetails = entityManager.find(UserDetails.class, username);
        if (userDetails == null) {
            return false;
        }
        return Objects.equals(userDetails.getPassword(), newPassword);
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        entityManager.persist(new UserDetails(username, newPassword));
    }
}
