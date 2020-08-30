package com.baeldung.ratelimiting.bucket4japp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baeldung.ratelimiting.bucket4japp.interceptor.RateLimitInterceptor;

@SpringBootApplication(scanBasePackages = "com.baeldung.ratelimiting", exclude = { 
        DataSourceAutoConfiguration.class,
        SecurityAutoConfiguration.class
})
public class Bucket4jRateLimitApp implements WebMvcConfigurer {
    
    @Autowired
    @Lazy
    private RateLimitInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
            .addPathPatterns("/api/v1/area/**");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Bucket4jRateLimitApp.class)
            .properties("spring.config.location=classpath:ratelimiting/application-bucket4j.yml")
            .run(args);
    }
}
