package com.baeldung.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.baeldung.security.web.MySavedRequestAwareAuthenticationSuccessHandler;
import com.baeldung.security.web.RestAuthenticationEntryPoint;
import com.baeldung.web.error.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@ComponentScan("com.baeldung.security")
public class SecurityJavaConfig {

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;

    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
            .password(encoder().encode("adminPass"))
            .roles("ADMIN")
            .build();
        UserDetails user = User.withUsername("user")
            .password(encoder().encode("userPass"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests (authorizeRequests -> authorizeRequests
            .requestMatchers("/login").anonymous()
            .requestMatchers("/dashboard").authenticated()
            .requestMatchers("/settings").fullyAuthenticated()
            .requestMatchers("/api/csrfAttacker*").permitAll()
            .requestMatchers("/api/customer/**").permitAll()
            .requestMatchers("/api/foos/**").authenticated()
            .requestMatchers("/api/async/**").permitAll()
            .requestMatchers("/public/**").permitAll()
            .requestMatchers("/private/**").denyAll()
            .requestMatchers("/auth/admin/*").hasAuthority("ADMIN")
            .requestMatchers("/auth/*").hasAnyAuthority("ADMIN", "USER")
            .requestMatchers("/api/admin/**").hasRole("ADMIN"))
            .formLogin(formLogin -> formLogin.successHandler(mySuccessHandler).failureHandler(myFailureHandler))
            .httpBasic(withDefaults())
            .logout(logout -> logout.permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("async-");
        return executor;
    }

    @Bean
    public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor delegate) {
        return new DelegatingSecurityContextAsyncTaskExecutor(delegate);
    }
}