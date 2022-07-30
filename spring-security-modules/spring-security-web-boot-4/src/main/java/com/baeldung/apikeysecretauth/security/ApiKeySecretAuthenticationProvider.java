package com.baeldung.apikeysecretauth.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.baeldung.apikeysecretauth.model.User;
import com.baeldung.apikeysecretauth.repository.UserKeysRepository;
import com.baeldung.apikeysecretauth.repository.model.UserKeysData;

public class ApiKeySecretAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserKeysRepository userKeysRepository;

    public ApiKeySecretAuthenticationProvider(PasswordEncoder passwordEncoder, UserKeysRepository userKeysRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userKeysRepository = userKeysRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApiKeySecretAuthenticationToken token = (ApiKeySecretAuthenticationToken) authentication;
        UserKeysData userKey = userKeysRepository.findById(token.getApiKey())
            .orElseThrow(() -> new BadCredentialsException("Invalid API KEY"));

        if (!passwordEncoder.matches(token.getApiSecret(), userKey.getApiSecret())) {
            throw new BadCredentialsException("Invalid API SECRET");
        }

        User user = new User(userKey.getAppUser());
        return new ApiKeySecretAuthenticationToken(user, extractAuthorities(userKey));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(ApiKeySecretAuthenticationToken.class);
    }

    private static Set<GrantedAuthority> extractAuthorities(UserKeysData userKey) {
        return userKey.getPermissions()
            .stream()
            .map(GrantedAuthority.class::cast)
            .collect(Collectors.toSet());
    }
}
