package org.baeldung.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.VerificationToken;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
    //NOV 5th
   // public User findByVerificationToken(VerificationToken token);
    //OCT 21
    public void delete(User user);
}
