package com.baeldung.spring;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.web.controller", "com.baeldung.persistence.service", "com.baeldung.persistence.dao" })
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    Environment env;

    public MvcConfig() {
        super();
    }

    // API

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/login.html");
        registry.addViewController("/home.html");

    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // For examples using Spring 4.1.0
        if ((env.getProperty("resource.handler.conf")).equals("4.1.0")) {
            registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(3600).resourceChain(true).addResolver(new EncodedResourceResolver()).addResolver(new PathResourceResolver());
            registry.addResourceHandler("/resources/**").addResourceLocations("/resources/", "classpath:/other-resources/").setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
            registry.addResourceHandler("/files/**").addResourceLocations("file:/Users/Elena/").setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
            registry.addResourceHandler("/other-files/**").addResourceLocations("file:/Users/Elena/").setCachePeriod(3600).resourceChain(true).addResolver(new EncodedResourceResolver());
        }
        // For examples using Spring 4.0.7
        else if ((env.getProperty("resource.handler.conf")).equals("4.0.7")) {
            registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources/", "classpath:/other-resources/");
            registry.addResourceHandler("/files/**").addResourceLocations("file:/Users/Elena/");

        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        ResourceUrlEncodingFilter filter = new ResourceUrlEncodingFilter();

        return filter;
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }

}