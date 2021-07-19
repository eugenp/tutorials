package com.baeldung.checkrolejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") String id) {
        return "user";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }

    @GetMapping("v2/user/{id}")
    public String getUserUsingSecurityContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "user";
        }

        throw new UnauthorizedException();
    }

    @GetMapping("v2/users")
    public String getUsersUsingDetailsService() {
        UserDetails details = userDetailsService.loadUserByUsername("mike");
        if (details != null && details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "users";
        }

        throw new UnauthorizedException();
    }

    @GetMapping("v3/users")
    public String getUsers(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "users";
        }

        throw new UnauthorizedException();
    }
}
