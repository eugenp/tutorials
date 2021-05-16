package com.baeldung.roles.custom.persistence;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import com.baeldung.roles.custom.persistence.dao.OrganizationRepository;
import com.baeldung.roles.custom.persistence.dao.PrivilegeRepository;
import com.baeldung.roles.custom.persistence.dao.UserRepository;
import com.baeldung.roles.custom.persistence.model.Organization;
import com.baeldung.roles.custom.persistence.model.Privilege;
import com.baeldung.roles.custom.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SetupData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        initOrganizations();
        initPrivileges();
        initUsers();
    }

    private void initUsers() {
        final Privilege privilege1 = privilegeRepository.findByName("FOO_READ_PRIVILEGE");
        final Privilege privilege2 = privilegeRepository.findByName("FOO_WRITE_PRIVILEGE");

        final User user1 = new User();
        user1.setUsername("john");
        user1.setPassword(encoder.encode("123"));
        user1.setPrivileges(new HashSet<>(Arrays.asList(privilege1)));
        user1.setOrganization(organizationRepository.findByName("FirstOrg"));
        userRepository.save(user1);

        final User user2 = new User();
        user2.setUsername("tom");
        user2.setPassword(encoder.encode("111"));
        user2.setPrivileges(new HashSet<>(Arrays.asList(privilege1, privilege2)));
        user2.setOrganization(organizationRepository.findByName("SecondOrg"));
        userRepository.save(user2);
    }

    private void initOrganizations() {
        final Organization org1 = new Organization("FirstOrg");
        organizationRepository.save(org1);

        final Organization org2 = new Organization("SecondOrg");
        organizationRepository.save(org2);
    }

    private void initPrivileges() {
        final Privilege privilege1 = new Privilege("FOO_READ_PRIVILEGE");
        privilegeRepository.save(privilege1);

        final Privilege privilege2 = new Privilege("FOO_WRITE_PRIVILEGE");
        privilegeRepository.save(privilege2);
    }
}
