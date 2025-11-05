package com.baeldung.athena.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.athena.service.QueryService.User;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final QueryService queryService;

    public List<User> getUsersByName(@NonNull final String name) {
        final var query = "SELECT * FROM users WHERE name = ?";
        return queryService.execute(query, List.of(name), User.class);
    }

}
