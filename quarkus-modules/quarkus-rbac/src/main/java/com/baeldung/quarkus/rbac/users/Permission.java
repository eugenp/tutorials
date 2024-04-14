package com.baeldung.quarkus.rbac.users;

import java.util.Arrays;

public enum Permission {

    ADMIN("Admin"),
    USER("User"),
    GUEST("Guest");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public static Permission from(String permission) {
        return Arrays.stream(Permission.values()).filter(p -> p.getPermission().equalsIgnoreCase(permission)).findFirst().orElse(null);
    }
}
