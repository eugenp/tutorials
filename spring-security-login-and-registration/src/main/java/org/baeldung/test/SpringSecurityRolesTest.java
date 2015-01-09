package org.baeldung.test;

import java.util.Arrays;

import org.baeldung.persistence.dao.PrivilegeRepository;
import org.baeldung.persistence.dao.RoleRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Privilege;
import org.baeldung.persistence.model.Role;
import org.baeldung.persistence.model.User;
import org.baeldung.spring.AppConfig;
import org.baeldung.spring.MvcConfig;
import org.baeldung.spring.PersistenceJPAConfig;
import org.baeldung.spring.SecSecurityConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, MvcConfig.class, PersistenceJPAConfig.class, SecSecurityConfig.class})
public class SpringSecurityRolesTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
    
    private User user;
    private Role role;
    private Privilege privilege;
    
    @Before
    public void init(){
        privilege = new Privilege("TEST_PRIVILEGE");
        privilegeRepository.save(privilege);
        
        role = new Role("TEST_ROLE");
        roleRepository.save(role);
        
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Arrays.asList(role));
        user.setEnabled(true);
        userRepository.save(user);
    }
    
    @After
    public void cleanUp(){
        privilegeRepository.delete(privilege);
        roleRepository.delete(role);
        userRepository.delete(user);
    }
    
    @Test
    public void testDeleteUser(){
        userRepository.delete(user);
        System.out.println(roleRepository.findByName(role.getName()));
    }
    
    @Test
    public void testDeleteRole(){
        roleRepository.delete(role);
        System.out.println(privilegeRepository.findByName(privilege.getName()));
        System.out.println(userRepository.findByEmail(user.getEmail()));
    }
    
    @Test
    public void testDeletePrivilege(){
        privilegeRepository.delete(privilege);
        System.out.println(roleRepository.findByName(role.getName()));
    }
}
