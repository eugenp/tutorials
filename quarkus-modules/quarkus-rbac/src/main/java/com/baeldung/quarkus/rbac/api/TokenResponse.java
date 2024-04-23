package com.baeldung.quarkus.rbac.api;

public record TokenResponse(String token, String expiresIn){
}
