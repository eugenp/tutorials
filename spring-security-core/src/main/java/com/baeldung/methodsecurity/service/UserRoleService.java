package com.baeldung.methodsecurity.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baeldung.methodsecurity.annotation.IsViewer;
import com.baeldung.methodsecurity.entity.CustomUser;
import com.baeldung.methodsecurity.repository.UserRoleRepository;

@Service
public class UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Secured("ROLE_VIEWER")
    public String getUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    @Secured({ "ROLE_VIEWER", "ROLE_EDITOR" })
    public boolean isValidUsername(String username) {
        return userRoleRepository.isValidUsername(username);
    }

    @RolesAllowed("ROLE_VIEWER")
    public String getUsername2() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    @RolesAllowed({ "ROLE_VIEWER", "ROLE_EDITOR" })
    public boolean isValidUsername2(String username) {
        return userRoleRepository.isValidUsername(username);
    }

    @PreAuthorize("hasRole('ROLE_VIEWER')")
    public String getUsernameInUpperCase() {
        return getUsername().toUpperCase();
    }

    @PreAuthorize("hasAuthority('SYS_ADMIN')")
    public String getUsernameLC() {
        return getUsername().toLowerCase();
    }

    @PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR')")
    public boolean isValidUsername3(String username) {
        return userRoleRepository.isValidUsername(username);
    }

    @PreAuthorize("#username == authentication.principal.username")
    public String getMyRoles(String username) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(","));
    }

    @PostAuthorize("#username == authentication.principal.username")
    public String getMyRoles2(String username) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.joining(","));
    }

    @PostAuthorize("returnObject.username == authentication.principal.nickName")
    public CustomUser loadUserDetail(String username) {
        return userRoleRepository.loadUserByUserName(username);
    }

    @PreFilter("filterObject != authentication.principal.username")
    public String joinUsernames(List<String> usernames) {
        return usernames.stream().collect(Collectors.joining(";"));
    }

    @PreFilter(value = "filterObject != authentication.principal.username", filterTarget = "usernames")
    public String joinUsernamesAndRoles(List<String> usernames, List<String> roles) {
        return usernames.stream().collect(Collectors.joining(";")) + ":" + roles.stream().collect(Collectors.joining(";"));
    }

    @PostFilter("filterObject != authentication.principal.username")
    public List<String> getAllUsernamesExceptCurrent() {
        return userRoleRepository.getAllUsernames();
    }

    @IsViewer
    public String getUsername4() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PostAuthorize("returnObject.username == authentication.principal.nickName")
    public CustomUser securedLoadUserDetail(String username) {
        return userRoleRepository.loadUserByUserName(username);
    }

}
