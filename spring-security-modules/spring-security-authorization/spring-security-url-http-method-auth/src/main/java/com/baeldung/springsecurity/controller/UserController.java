package com.baeldung.springsecurity.controller;

import com.baeldung.springsecurity.dto.request.RegisterRequestDto;
import com.baeldung.springsecurity.dto.UserProfileDto;
import com.baeldung.springsecurity.service.UserService;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request) {
        String result = userService.register(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserProfileDto> profile(Authentication authentication) {
        UserProfileDto userProfileDto = userService.profile(authentication.getName());
        return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
    }
}
