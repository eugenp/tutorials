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
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent>{
    
    boolean alreadyExist = false;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadyExist)
            return;
        if(roleRepository.findAll().size() > 0 || privilegeRepository.findAll().size() > 0)
            return;
        
        //== create initial roles
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        
        //== create initial privileges
        Privilege readPrivilege = new Privilege("READ_PRIVILEGE");
        Privilege writePrivilege = new Privilege("WRITE_PRIVILEGE");
                
        //== link roles and privileges
        Privilege[] adminPrivileges = {readPrivilege, writePrivilege};
        admin.setPrivileges(Arrays.asList(adminPrivileges));
        
        Privilege[] userPrivileges = {readPrivilege};
        user.setPrivileges(Arrays.asList(userPrivileges));
        
        //== save to database
        privilegeRepository.save(readPrivilege);
        privilegeRepository.save(writePrivilege);
        roleRepository.save(admin);
        roleRepository.save(user);
        
        alreadyExist = true;
    }

}