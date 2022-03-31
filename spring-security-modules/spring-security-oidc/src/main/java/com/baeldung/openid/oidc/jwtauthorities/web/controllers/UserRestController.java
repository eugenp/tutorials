package com.baeldung.openid.oidc.jwtauthorities.web.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.openid.oidc.jwtauthorities.config.AccountToken;
import com.baeldung.openid.oidc.jwtauthorities.domain.Account;

@RestController
@RequestMapping("/user")
public class UserRestController {
        
    @GetMapping("/authorities")
    @PreAuthorize("hasAuthority(@jwtGrantedAuthoritiesPrefix + 'profile.read')")
    public Map<String,Object> getPrincipalInfo( JwtAuthenticationToken principal) {
        
        Collection<String> authorities = principal.getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList());
        
        Map<String,Object> info = new HashMap<>();
        info.put("name", principal.getName());
        info.put("authorities", authorities);
        info.put("tokenAttributes", principal.getTokenAttributes());
        
        if ( principal instanceof AccountToken ) {
            info.put( "account", ((AccountToken)principal).getAccount());
        }
        
        return info;
    }
    
    @GetMapping("/account/{accountNumber}")
    @PreAuthorize("authentication.account.accountNumber == #accountNumber")
    public Account getAccountById(@PathVariable("accountNumber") String accountNumber, AccountToken authentication) {
        return authentication.getAccount();
    }
}
