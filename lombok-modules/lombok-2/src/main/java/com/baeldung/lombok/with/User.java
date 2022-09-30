package com.baeldung.lombok.with;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@Getter
@AllArgsConstructor
public class User {
    private final String username;
    private final String emailAddress;
    @With
    private final boolean isAuthenticated;
}