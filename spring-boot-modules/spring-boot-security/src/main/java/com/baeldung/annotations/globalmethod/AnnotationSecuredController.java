package com.baeldung.annotations.globalmethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class AnnotationSecuredController {

    @Autowired
    DifferentClass differentClass;

    @GetMapping("/public")
    public String publicHello() {
        return "Hello Public";
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/admin")
    public String adminHello() {
        return "Hello Admin";
    }

    @RolesAllowed("USER")
    @GetMapping("/protected")
    public String jsr250Hello() {
        return "Hello Jsr250";
    }

    @GetMapping("/indirect")
    public String indirectHello() {
        return jsr250Hello();
    }

    @GetMapping("/differentclass")
    public String differentClassHello() {
        return differentClass.differentJsr250Hello();
    }

    @PreAuthorize("hasRole('USER')")
    public String preAuthorizeHello() {
        return "Hello PreAuthorize";
    }

}
