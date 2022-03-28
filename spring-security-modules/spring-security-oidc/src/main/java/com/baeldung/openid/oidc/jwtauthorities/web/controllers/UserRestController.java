package com.baeldung.openid.oidc.jwtauthorities.web.controllers;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {
    
    @GetMapping("/authorities")
    public PrincipalInfo getPrincipalInfo( JwtAuthenticationToken principal) {
        
        Collection<String> authorities = principal.getAuthorities()
          .stream()
          .map(GrantedAuthority::getAuthority)
          .collect(Collectors.toList());
        
        return new PrincipalInfo(principal.getName(), authorities, principal.getTokenAttributes());
    }
    
    private class PrincipalInfo {
        
        private final String name;
        private final Collection<String> grantedAuthorities;
        private final Map<String,Object> tokenAttributes;
        
        public PrincipalInfo(String name, Collection<String> grantedAuthorities, Map<String, Object> tokenAttributes) {
            super();
            this.name = name;
            this.grantedAuthorities = grantedAuthorities;
            this.tokenAttributes = tokenAttributes;
        }
        
        public String getName() {
            return name;
        }
        public Collection<String> getGrantedAuthorities() {
            return grantedAuthorities;
        }
        public Map<String, Object> getTokenAttributes() {
            return tokenAttributes;
        }
        
        
        
    }
}
