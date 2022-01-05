package com.baeldung.swaggerkeycloak;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    // otherwise, we'll get an error 'permitAll only works with HttpSecurity.authorizeRequests()'
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
          .csrf().disable()
          .authorizeRequests()
          // we can set up authorization here alternatively to @Secured methods
          .antMatchers(HttpMethod.OPTIONS).permitAll()
          .antMatchers("/api/**").authenticated()
          // force authentication for all requests (and use global method security)
          .anyRequest().permitAll();
    }

    /*
     * re-configure Spring Security to use
     * registers the KeycloakAuthenticationProvider with the authentication manager
     */
    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider provider = keycloakAuthenticationProvider();
        provider.setGrantedAuthoritiesMapper(authoritiesMapper());
        auth.authenticationProvider(provider);
    }

    GrantedAuthoritiesMapper authoritiesMapper() {
        SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
        mapper.setPrefix("ROLE_"); // Spring Security adds a prefix to the authority/role names (we use the default here)
        mapper.setConvertToUpperCase(true); // convert names to uppercase
        mapper.setDefaultAuthority("ROLE_ANONYMOUS"); // set a default authority
        return mapper;
    }

}
