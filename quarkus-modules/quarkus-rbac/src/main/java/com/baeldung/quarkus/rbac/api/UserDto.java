package com.baeldung.quarkus.rbac.api;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UserDto(@NotNull String username, @NotNull String password, @Size(min = 1) Set<String> roles, @Email String email) { }
