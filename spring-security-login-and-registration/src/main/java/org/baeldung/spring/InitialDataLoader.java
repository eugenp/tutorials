package org.baeldung.spring;

import java.util.Arrays;

import org.baeldung.persistence.dao.PrivilegeRepository;
import org.baeldung.persistence.dao.RoleRepository;
import org.baeldung.persistence.model.Privilege;
import org.baeldung.persistence.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadyExist = false;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadyExist)
            return;
        if (roleRepository.count() > 0 || privilegeRepository.count() > 0)
            return;

        // == create initial privileges
        final Privilege readPrivilege = new Privilege("READ_PRIVILEGE");
        final Privilege writePrivilege = new Privilege("WRITE_PRIVILEGE");
        privilegeRepository.save(readPrivilege);
        privilegeRepository.save(writePrivilege);

        // == create initial roles
        final Role admin = new Role("ROLE_ADMIN");
        final Role user = new Role("ROLE_USER");

        // == link roles and privileges
        admin.setPrivileges(Arrays.asList(readPrivilege, writePrivilege));
        user.setPrivileges(Arrays.asList(readPrivilege));

        roleRepository.save(admin);
        roleRepository.save(user);

        alreadyExist = true;
    }

    private final void createPrivilegeIfNotFound(final Privilege privilege) {
        if (privilegeRepository.findByName(privilege.getName()) != null) {
            privilegeRepository.save(privilege);
        }
    }

}