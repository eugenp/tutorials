package com.baeldung.apikeysecretauth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.baeldung.apikeysecretauth.repository.UserKeysRepository;
import com.baeldung.apikeysecretauth.security.ApiKeySecretAuthenticationProvider;
import com.baeldung.apikeysecretauth.security.ApiKeySecretFilter;
import com.baeldung.apikeysecretauth.security.RestAuthenticationEntryPoint;

@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserKeysRepository userKeysRepository;

    public SpringWebSecurityConfig(UserKeysRepository userKeysRepository) {
        this.userKeysRepository = userKeysRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final AuthenticationEntryPoint restAuthenticationEntryPoint = new RestAuthenticationEntryPoint();

        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(new ApiKeySecretFilter(authenticationManager(), restAuthenticationEntryPoint), BasicAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new ApiKeySecretAuthenticationProvider(passwordEncoder(), userKeysRepository));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
