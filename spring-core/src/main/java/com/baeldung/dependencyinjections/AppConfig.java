package com.baeldung.dependencyinjections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public WebsiteControllerConstructorInjection websiteControllerConstructorInjection() {
        return new WebsiteControllerConstructorInjection(homepageService());
    }

    @Bean
    public HomepageService homepageService() {
        return new HomepageService();
    }

    @Bean
    public WebsiteControllerSetterInjection websiteControllerSetterInjection() {
        return new WebsiteControllerSetterInjection();
    }
}
