package com.baeldung.keycloak.keycloaksoap;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // Extract client roles with ROLE_ prefix
        authorities.addAll(extractClientRoles(jwt));

        return authorities;
    }



    private Collection<GrantedAuthority> extractClientRoles(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null || resourceAccess.isEmpty()) {
            return Collections.emptyList();
        }
        // Replace this with your actual client ID from Keycloak
        String clientId = "baeldung-soap-services";

        Map<String, Object> client = (Map<String, Object>) resourceAccess.get(clientId);
        if (client == null || client.isEmpty()) {
            return Collections.emptyList();
        }
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) client.get("roles");
        if (roles == null) {
            return Collections.emptyList();
        }
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Add ROLE_ prefix here
            .collect(Collectors.toList());
    }
}
