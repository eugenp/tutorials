package com.baeldung.boot.keycloak.resourceserver;

import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ch4mp&#64;c4-soft.com
 */
@RestController
public class MeController {
    @GetMapping("/me")
    public UserInfoDto getGretting(JwtAuthenticationToken auth) {
        return new UserInfoDto(
            auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME),
            auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
    }

    public static record UserInfoDto(String name, List<String> roles) {
    }
}
