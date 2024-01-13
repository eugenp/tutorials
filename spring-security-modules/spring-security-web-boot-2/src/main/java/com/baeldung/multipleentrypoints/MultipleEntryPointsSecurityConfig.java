package com.baeldung.multipleentrypoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class MultipleEntryPointsSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password(encoder().encode("userPass")).roles("USER").build());
        manager.createUser(User.withUsername("admin").password(encoder().encode("adminPass")).roles("ADMIN").build());
        return manager;
    }
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainApp1(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/admin/**")
                    .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMIN"))
                    .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.authenticationEntryPoint(authenticationEntryPoint()))
                    .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                            httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/403"));
            return http.build();
        }

        @Bean
        public AuthenticationEntryPoint authenticationEntryPoint(){
            BasicAuthenticationEntryPoint entryPoint = new  BasicAuthenticationEntryPoint();
            entryPoint.setRealmName("admin realm");
            return entryPoint;
        }
    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainApp2(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/user/**")
                    .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                            authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/user/**")).hasRole("USER"))
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer.loginProcessingUrl("/user/login")
                                .failureUrl("/userLogin?error=loginError")
                                .defaultSuccessUrl("/user/myUserPage"))
                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutUrl("/user/logout")
                                .logoutSuccessUrl("/multipleHttpLinks")
                                .deleteCookies("JSESSIONID"))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.defaultAuthenticationEntryPointFor(loginUrlauthenticationEntryPointWithWarning(), new AntPathRequestMatcher("/user/private/**"))
                                .defaultAuthenticationEntryPointFor(loginUrlauthenticationEntryPoint(), new AntPathRequestMatcher("/user/general/**"))
                                .accessDeniedPage("/403"))
                .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }
        
        @Bean
        public AuthenticationEntryPoint loginUrlauthenticationEntryPoint(){
            return new LoginUrlAuthenticationEntryPoint("/userLogin");
        }
        
        @Bean
        public AuthenticationEntryPoint loginUrlauthenticationEntryPointWithWarning(){
            return new LoginUrlAuthenticationEntryPoint("/userLoginWithWarning");
        }
    }

    @Configuration
    @Order(3)
    public static class App3ConfigurationAdapter {

        @Bean
        public SecurityFilterChain filterChainApp3(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/guest/**")
                    .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/guest/**")).permitAll());
            return http.build();
        }
    }

}
