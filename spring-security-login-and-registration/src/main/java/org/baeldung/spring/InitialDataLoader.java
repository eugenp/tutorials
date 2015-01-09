package org.baeldung.spring;

import java.util.Arrays;

import org.baeldung.persistence.dao.PrivilegeRepository;
import org.baeldung.persistence.dao.RoleRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Privilege;
import org.baeldung.persistence.model.Role;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadyExist = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadyExist)
            return;

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        // == create initial roles
        final Role admin = createRoleIfNotFound("ROLE_ADMIN");
        final Role userRole = createRoleIfNotFound("ROLE_USER");

        // == link roles and privileges
        admin.setPrivileges(Arrays.asList(readPrivilege, writePrivilege));
        userRole.setPrivileges(Arrays.asList(readPrivilege));

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("test"));
        user.setEmail("test@test.com");
        user.setRoles(Arrays.asList(admin));
        user.setEnabled(true);
        userRepository.save(user);

        alreadyExist = true;
    }

    @Transactional
    private final Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private final Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

}