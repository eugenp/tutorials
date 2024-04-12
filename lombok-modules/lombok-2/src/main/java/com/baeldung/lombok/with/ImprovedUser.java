package com.baeldung.lombok.with;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.With;

@With
@AllArgsConstructor
public class ImprovedUser {
    @NonNull
    private final String username;
    @NonNull
    private final String emailAddress;
}