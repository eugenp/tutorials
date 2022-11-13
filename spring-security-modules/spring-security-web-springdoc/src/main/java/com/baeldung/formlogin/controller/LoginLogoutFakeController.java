package com.baeldung.formlogin.controller;

import com.baeldung.formlogin.model.LoginDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginLogoutFakeController {

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void login(@RequestBody LoginDTO request) {
        throw new IllegalStateException("Spring Security handles authentication");
    }

    @PostMapping(value = "/logout")
    public void logout() {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }
}
