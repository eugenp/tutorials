package com.baeldung.swagger2bootmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@EnableWebMvc
public class Swagger2Config {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Swagger Array")
            .description("This is a sample Swagger description for an Array server")
            .version("1.0.0")
            .license(new License().name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}