package org.baeldung.persistence;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.baeldung.persistence.dao.OrganizationRepository;
import org.baeldung.persistence.dao.PrivilegeRepository;
import org.baeldung.persistence.dao.RoleRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Organization;
import org.baeldung.persistence.model.Privilege;
import org.baeldung.persistence.model.Role;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupData {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @PostConstruct
    public void init() {
        initPrivileges();
        initRoles();
        initOrganizations();
        initUsers();
    }

    private void initUsers() {
        final Role role1 = roleRepository.findByName("USER_ROLE");
        final Role role2 = roleRepository.findByName("ADMIN_ROLE");
        //
        final User user1 = new User();
        user1.setUsername("john");
        user1.setPassword("123");
        user1.setRoles(new HashSet<Role>(Arrays.asList(role1)));
        user1.setOrganization(organizationRepository.findByName("FirstOrg"));
        userRepository.save(user1);
        //
        final User user2 = new User();
        user2.setUsername("tom");
        user2.setPassword("111");
        user2.setRoles(new HashSet<Role>(Arrays.asList(role2)));
        user2.setOrganization(organizationRepository.findByName("SecondOrg"));
        userRepository.save(user2);
    }

    private void initOrganizations() {
        final Organization org1 = new Organization("FirstOrg");
        organizationRepository.save(org1);
        //
        final Organization org2 = new Organization("SecondOrg");
        organizationRepository.save(org2);

    }

    private void initRoles() {
        final Privilege privilege1 = privilegeRepository.findByName("FOO_READ_PRIVILEGE");
        final Privilege privilege2 = privilegeRepository.findByName("FOO_WRITE_PRIVILEGE");
        //
        final Role role1 = new Role("USER_ROLE");
        role1.setPrivileges(new HashSet<Privilege>(Arrays.asList(privilege1)));
        roleRepository.save(role1);
        //
        final Role role2 = new Role("ADMIN_ROLE");
        role2.setPrivileges(new HashSet<Privilege>(Arrays.asList(privilege1, privilege2)));
        roleRepository.save(role2);
    }

    private void initPrivileges() {
        final Privilege privilege1 = new Privilege("FOO_READ_PRIVILEGE");
        privilegeRepository.save(privilege1);
        //
        final Privilege privilege2 = new Privilege("FOO_WRITE_PRIVILEGE");
        privilegeRepository.save(privilege2);
    }
}
