package com.baeldung.config;

import com.baeldung.events.AuthorEventHandler;
import com.baeldung.events.BookEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig() {
        super();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    AuthorEventHandler authorEventHandler() {
        return new AuthorEventHandler();
    }

    @Bean
    BookEventHandler bookEventHandler() {
        return new BookEventHandler();
    }

}
