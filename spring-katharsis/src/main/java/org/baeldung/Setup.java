package org.baeldung;

import javax.annotation.PostConstruct;

import org.baeldung.persistence.domain.model.Role;
import org.baeldung.persistence.domain.model.User;
import org.baeldung.persistence.domain.repository.RoleRepository;
import org.baeldung.persistence.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * The Class Setup.
 */
@Configuration
public class Setup {

    /** The role repository. */
    @Autowired
    RoleRepository roleRepository;

    /** The user repository. */
    @Autowired
    UserRepository userRepository;

    /**
     * Inits the.
     */
    @PostConstruct
    public void init() {
        Role role1 = new Role(1L, "admin");
        Role role2 = new Role(2L, "superadmin");

        User user1 = new User(1L, "John", "john@test.com");

        role1.setUser(user1);
        role2.setUser(user1);

        user1.getRoles()
            .add(role1);
        user1.getRoles()
            .add(role2);

        roleRepository.save(role1);
        roleRepository.save(role2);

        userRepository.save(user1);
    }
}