package org.baeldung.testmethodsecurity.service;

import org.baeldung.testmethodsecurity.entity.CustomUser;
import org.baeldung.testmethodsecurity.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    
    @Autowired
    UserRoleRepository userRoleRepository;
    
    @PreAuthorize("hasRole('ROLE_VIEWER') or hasAuthority('SYS_ADMIN')")
    public String getUsername(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }
    
    @PostAuthorize("returnObject.username == authentication.principal.nickName")
    public CustomUser loadUserDetail(String username){
        return userRoleRepository.loadUserByUserName(username);
    }
    
}
