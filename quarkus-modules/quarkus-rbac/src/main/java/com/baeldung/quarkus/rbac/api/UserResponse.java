package com.baeldung.quarkus.rbac.api;

import java.util.Set;

public record UserResponse(String username, Set<String> roles) { }

