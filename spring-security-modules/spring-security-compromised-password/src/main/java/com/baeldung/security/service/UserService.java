package com.baeldung.security.service;

import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.authentication.password.CompromisedPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baeldung.security.dto.UserCreationRequestDto;
import com.baeldung.security.entity.User;
import com.baeldung.security.exception.AccountAlreadyExistsException;
import com.baeldung.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompromisedPasswordChecker compromisedPasswordChecker;

    public void create(UserCreationRequestDto userCreationRequest) {
        String emailId = userCreationRequest.getEmailId();
        if (userRepository.existsByEmailId(emailId)) {
            throw new AccountAlreadyExistsException("User account with provided email-id already exists");
        }

        String password = userCreationRequest.getPassword();
        CompromisedPasswordDecision decision = compromisedPasswordChecker.check(password);
        if (decision.isCompromised()) {
            throw new CompromisedPasswordException("The provided password is compromised and cannot be used.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setEmailId(emailId);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

}