package com.baeldung.annotations.service.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    boolean authenticate(String username, String password);

}