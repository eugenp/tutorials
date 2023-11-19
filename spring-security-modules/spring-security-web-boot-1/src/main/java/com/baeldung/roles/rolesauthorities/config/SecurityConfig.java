package com.baeldung.roles.rolesauthorities.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.baeldung.roles.rolesauthorities.CustomAuthenticationProvider;
import com.baeldung.roles.rolesauthorities.persistence.UserRepository;

@Configuration
@ComponentScan(basePackages = {"com.baeldung.rolesauthorities"})
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private LogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authProvider())
            .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .antMatchers("/resources/**");
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/login*", "/logout*", "/protectedbynothing*", "/home*")
            .permitAll()
            .antMatchers("/protectedbyrole")
            .hasRole("USER")
            .antMatchers("/protectedbyauthority")
            .hasAuthority("READ_PRIVILEGE")
            .and()
            .formLogin()
            .loginPage("/login")
            .failureUrl("/login?error=true")
            .permitAll()
            .and()
            .logout()
            .logoutSuccessHandler(myLogoutSuccessHandler)
            .invalidateHttpSession(false)
            .logoutSuccessUrl("/logout.html?logSucc=true")
            .deleteCookies("JSESSIONID")
            .permitAll();
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider
        	= new CustomAuthenticationProvider(userRepository, userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}