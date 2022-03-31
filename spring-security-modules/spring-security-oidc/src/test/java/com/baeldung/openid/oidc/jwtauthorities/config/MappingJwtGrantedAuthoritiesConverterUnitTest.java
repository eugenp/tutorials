package com.baeldung.openid.oidc.jwtauthorities.config;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

class MappingJwtGrantedAuthoritiesConverterUnitTest {

    @Test
    void testGivenConverterWithScopeMap_whenConvert_thenResultHasMappedAuthorities() {
        Jwt jwt = Jwt.withTokenValue("NOTUSED")
          .header("typ", "JWT")
          .subject("user")
          .claim("scp", "openid email profile")
          .build();
        
        Map<String, String> scopeMap = new HashMap<>();
        scopeMap.put("profile", "profile.read");        
        MappingJwtGrantedAuthoritiesConverter converter = new MappingJwtGrantedAuthoritiesConverter(scopeMap);
        Collection<GrantedAuthority> result = converter.convert(jwt);
        
        assertTrue("Result must contain the authoriry 'SCOPE_profile.read'", 
         result.contains(new SimpleGrantedAuthority("SCOPE_profile.read")));
    }

    @Test
    void testGivenConverterWithCustomScopeClaim_whenConvert_thenResultHasAuthorities() {
        Jwt jwt = Jwt.withTokenValue("NOTUSED")
          .header("typ", "JWT")
          .subject("user")
          .claim("myscope_claim", "openid email profile")
          .build();
        
        Map<String, String> scopeMap = new HashMap<>();
        MappingJwtGrantedAuthoritiesConverter converter = new MappingJwtGrantedAuthoritiesConverter(scopeMap);
        converter.setAuthoritiesClaimName("myscope_claim");
        Collection<GrantedAuthority> result = converter.convert(jwt);
        
        assertTrue("Result must contain the authoriry 'SCOPE_profile'", 
         result.contains(new SimpleGrantedAuthority("SCOPE_profile")));
    }
    
    @Test
    void testGivenTokenWithNonMappedScope_whenConvert_thenResultHasOriginalScope() {
        Jwt jwt = Jwt.withTokenValue("NOTUSED")
          .header("typ", "JWT")
          .subject("user")
          .claim("scp", "openid email profile custom")
          .build();
        
        Map<String, String> scopeMap = new HashMap<>();
        scopeMap.put("profile", "profile.read");        
        MappingJwtGrantedAuthoritiesConverter converter = new MappingJwtGrantedAuthoritiesConverter(scopeMap);
        Collection<GrantedAuthority> result = converter.convert(jwt);
        
        assertTrue("Result must contain the authority SCOPE_custom", 
         result.contains(new SimpleGrantedAuthority("SCOPE_custom")));
    }
    

    @Test
    void testGivenConverterWithCustomPrefix_whenConvert_thenAllAuthoritiesMustHaveTheCustomPrefix() {
        Jwt jwt = Jwt.withTokenValue("NOTUSED")
          .header("typ", "JWT")
          .subject("user")
          .claim("scp", "openid email profile custom")
          .build();
        
        Map<String, String> scopeMap = new HashMap<>();
        scopeMap.put("profile", "profile.read");        
        MappingJwtGrantedAuthoritiesConverter converter = new MappingJwtGrantedAuthoritiesConverter(scopeMap);
        converter.setAuthorityPrefix("MY_SCOPE");
        Collection<GrantedAuthority> result = converter.convert(jwt);
        
        long count = result.stream()
          .map(GrantedAuthority::getAuthority)
          .filter(s -> !s.startsWith("MY_SCOPE"))
          .count();
        
        assertTrue("All authorities names must start with custom prefix", count == 0 );
    }

}
