package com.baeldung.cats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Automated Testing for OpenAPI Endpoints Using CATS", version = "1.0.0"), security = { @SecurityRequirement(name = "Authorization") })
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "Authorization", in = SecuritySchemeIn.HEADER, scheme = "Bearer")
public class CatsApplication {

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // return http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/swagger-ui-custom.html", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/webjars/**", "/swagger-ui/index.html", "/api-docs/**")
    // .permitAll()
    // .anyRequest()
    // .authenticated())
    // .build();
    // }

    public static void main(String[] args) {
        SpringApplication.run(CatsApplication.class, args);
    }
}