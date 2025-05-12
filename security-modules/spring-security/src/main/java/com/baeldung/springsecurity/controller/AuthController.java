package com.baeldung.springsecurity.controller;

import com.baeldung.springsecurity.dto.request.RegisterRequestDto;
import com.baeldung.springsecurity.dto.UserProfileDto;
import com.baeldung.springsecurity.service.AuthService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request) {
        String result = authService.register(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("profile")
    public ResponseEntity<UserProfileDto> profile(Authentication authentication) {
        UserProfileDto userProfileDto = authService.profile(authentication.getName());
        return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
    }
}
