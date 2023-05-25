package com.baeldung.sample.boundary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;
import static org.springframework.http.HttpHeaders.CONTENT_LANGUAGE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.IF_MATCH;
import static org.springframework.http.HttpHeaders.IF_NONE_MATCH;
import static org.springframework.http.HttpHeaders.LINK;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpHeaders.ORIGIN;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer(final CorsConfigurationData allowed) {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**")
                  .exposedHeaders(LOCATION, LINK)
                  // allow all HTTP request methods
                  .allowedMethods(stream(RequestMethod.values()).map(Enum::name).toArray(String[]::new)) //
                  // allow the commonly used headers
                  .allowedHeaders(ORIGIN, CONTENT_TYPE, CONTENT_LANGUAGE, ACCEPT, ACCEPT_LANGUAGE, IF_MATCH, IF_NONE_MATCH) //
                  // this is stage specific
                  .allowedOrigins(allowed.getOrigins())
                  .allowCredentials(allowed.isCredentials());
            }
        };
    }

}
