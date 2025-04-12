package com.baeldung.spring_security.service;

import com.baeldung.spring_security.dto.request.RegisterRequestDto;
import com.baeldung.spring_security.dto.UserProfileDto;
import com.baeldung.spring_security.entity.User;
import org.springframework.security.core.Authentication;

public interface IAuthService {
    String register(RegisterRequestDto request);
    UserProfileDto profile(Authentication authentication);
    User getUser(Authentication authentication);
}
