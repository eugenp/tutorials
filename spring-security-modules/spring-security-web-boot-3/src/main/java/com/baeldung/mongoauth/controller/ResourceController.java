package com.baeldung.mongoauth.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }

    @RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
    @GetMapping("/user")
    public String user() {
        return "Hello User!";
    }

}
