package com.baeldung.jhipster6.security;

import com.baeldung.jhipster6.domain.User;
import com.baeldung.jhipster6.security.dto.LoginRequest;
import com.baeldung.jhipster6.security.dto.LoginResponse;
import com.baeldung.jhipster6.service.UserService;
import com.baeldung.jhipster6.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    private final static Logger LOG = LoggerFactory.getLogger(CustomAuthenticationManager.class);

    private final String REMOTE_LOGIN_URL = "https://example.com/login";

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(authentication.getPrincipal().toString());
        loginRequest.setPassword(authentication.getCredentials().toString());

        try
        {
            ResponseEntity<LoginResponse> response =
                restTemplate.postForEntity(
                    REMOTE_LOGIN_URL,
                    loginRequest,
                    LoginResponse.class);

            if(response.getStatusCode().is2xxSuccessful())
            {
                //
                // Need to create a new local user if this is the  first time logging in; this
                // is required so they can be issued JWTs. We can use this flow to also keep
                // our local use entry up to date with data from the remote service if needed
                // (for example, if the first and last name might change, this is where we would
                // update the local user entry)
                //

                User user = userService.getUserWithAuthoritiesByLogin(authentication.getPrincipal().toString())
                                       .orElseGet(() -> userService.createUser(createUserDTO(response.getBody(), authentication)));
                return createAuthentication(authentication, user);
            }
            else
            {
                throw new BadCredentialsException("Invalid username or password");
            }
        }
        catch (Exception e)
        {
            LOG.warn("Failed to authenticate", e);
            throw new AuthenticationServiceException("Failed to login", e);
        }
    }

    /**
     * Creates a new authentication with basic roles
     * @param auth Contains auth details that will be copied into the new one.
     * @param user User object representing who is logging in
     * @return Authentication
     */
    private Authentication createAuthentication(Authentication auth, User user) {

        //
        // Honor any roles the user already has set; default is just USER role
        // but could be modified after account creation
        //

        Collection<? extends GrantedAuthority> authorities = user
            .getAuthorities()
            .stream()
            .map(a -> new SimpleGrantedAuthority(a.getName()))
            .collect(Collectors.toSet());

        UsernamePasswordAuthenticationToken token
            = new UsernamePasswordAuthenticationToken(
            user.getId(),
            auth.getCredentials().toString(),
            authorities);

        return token;
    }

    /**
     * Creates a new UserDTO with basic info.
     * @param loginResponse Response from peloton login API
     * @param authentication Contains user login info (namely username and password)
     * @return UserDTO
     */
    private UserDTO createUserDTO(LoginResponse loginResponse, Authentication authentication) {

        UserDTO dto = new UserDTO();

        dto.setActivated(true);
        dto.setEmail(loginResponse.getEmail());
        dto.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
        dto.setFirstName(loginResponse.getFirstName());
        dto.setLastName(loginResponse.getLastName());

        return dto;
    }
}
