package org.baeldung;

import javax.annotation.PostConstruct;

import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Setup {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void setupData() {
        final User user = new User();
        user.setFirstName("john");
        user.setLastName("doe");
        user.setEmail("john@test.com");
        userRepository.save(user);
    }

}