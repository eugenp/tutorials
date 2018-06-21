package org.baeldung.spring.config;

import org.baeldung.scopes.HelloMessageGenerator;
import org.baeldung.scopes.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@ComponentScan("org.baeldung.scopes")
@EnableWebMvc
public class ScopesConfig {
    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public HelloMessageGenerator requestMessage() {
        return new HelloMessageGenerator();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public HelloMessageGenerator sessionMessage() {
        return new HelloMessageGenerator();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public HelloMessageGenerator globalSessionMessage() {
        return new HelloMessageGenerator();
    }

    @Bean
    @Scope("prototype")
    public Person personPrototype() {
        return new Person();
    }

    @Bean
    @Scope("singleton")
    public Person personSingleton() {
        return new Person();
    }
}
