package com.baeldung.bff;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c4_soft.springaddons.security.oidc.starter.properties.SpringAddonsOidcProperties;

import jakarta.validation.constraints.NotEmpty;
import reactor.core.publisher.Mono;

@RestController
public class LoginOptionsController {
    private final List<LoginOptionDto> loginOptions;

    public LoginOptionsController(OAuth2ClientProperties clientProps, SpringAddonsOidcProperties addonsProperties) {
        final var clientAuthority = addonsProperties.getClient()
            .getClientUri()
            .getAuthority();
        this.loginOptions = clientProps.getRegistration()
            .entrySet()
            .stream()
            .filter(e -> "authorization_code".equals(e.getValue()
                .getAuthorizationGrantType()))
            .map(e -> {
                final var label = e.getValue()
                    .getProvider();
                final var loginUri = "%s/oauth2/authorization/%s".formatted(addonsProperties.getClient()
                    .getClientUri(), e.getKey());
                final var providerId = clientProps.getRegistration()
                    .get(e.getKey())
                    .getProvider();
                final var providerIssuerAuthority = URI.create(clientProps.getProvider()
                    .get(providerId)
                    .getIssuerUri())
                    .getAuthority();
                return new LoginOptionDto(label, loginUri, Objects.equals(clientAuthority, providerIssuerAuthority));
            })
            .toList();
    }

    @GetMapping(path = "/login-options", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<LoginOptionDto>> getLoginOptions() throws URISyntaxException {
        return Mono.just(this.loginOptions);
    }

    public static record LoginOptionDto(@NotEmpty String label, @NotEmpty String loginUri, boolean isSameAuthority) {
    }

}
