package com.baeldung.ratelimiting.bucket4japp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baeldung.ratelimiting.bucket4japp.interceptor.RateLimitingInterceptor;

@SpringBootApplication(scanBasePackages = "com.baeldung.ratelimiting", exclude = { 
        DataSourceAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
public class Bucket4jRateLimitingApp implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitingInterceptor())
            .addPathPatterns("/api/v1/area/**");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Bucket4jRateLimitingApp.class)
            .properties("spring.config.location=classpath:ratelimiting/application-bucket4j.yml")
            .run(args);
    }
}
