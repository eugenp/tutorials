package com.baeldung.spring.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.baeldung.web.controller" })
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/")
            .setViewName("index");
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");
        bean.setOrder(2);
        return bean;
    }
    
    @Bean
    public ViewResolver resourceBundleViewResolver() {
        final ResourceBundleViewResolver bean = new ResourceBundleViewResolver();
        bean.setBasename("views");
        bean.setOrder(0);
        return bean;
    }
    
    @Bean
    public ViewResolver xmlViewResolver() {
        final XmlViewResolver bean = new XmlViewResolver();
        bean.setLocation(new ClassPathResource("views.xml"));
        bean.setOrder(1);
        return bean;
    }
}