package com.baeldung.loginredirect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//if you use xsd configuration (spring-security-login-redirect.xml) please comment the annotations from this class, because it will overlap with configuration from this class
/*@Configuration
@EnableWebSecurity*/
class LoginRedirectSecurityConfig {

    private static final String LOGIN_USER = "/loginUser";

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
            .password(encoder().encode("user"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterAfter(new LoginPageFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                    authorizationManagerRequestMatcherRegistry.requestMatchers(LOGIN_USER).permitAll()
                            .requestMatchers("/user*").hasRole("USER"))
            .formLogin(httpSecurityFormLoginConfigurer ->
                    httpSecurityFormLoginConfigurer.loginPage(LOGIN_USER)
                            .loginProcessingUrl("/user_login")
                            .failureUrl("/loginUser?error=loginError")
                            .defaultSuccessUrl("/userMainPage").permitAll())
            .logout(httpSecurityLogoutConfigurer ->
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/user_logout")
                            .logoutSuccessUrl(LOGIN_USER)
                            .deleteCookies("JSESSIONID"))
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
