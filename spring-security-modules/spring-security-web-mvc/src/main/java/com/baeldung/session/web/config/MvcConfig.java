package com.baeldung.session.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/anonymous").setViewName("view/anonymous");

        registry.addViewController("/login").setViewName("view/login");
        registry.addViewController("/homepage").setViewName("view/homepage");
        registry.addViewController("/sessionExpired").setViewName("view/sessionExpired");
        registry.addViewController("/invalidSession").setViewName("view/invalidSession");
        registry.addViewController("/console").setViewName("view/console");
    }


    /* 
     * Spring Boot supports configuring a ViewResolver with properties
     */
//    @Bean
//    public ViewResolver viewResolver() {
//        final InternalResourceViewResolver bean = new InternalResourceViewResolver();
//
//        bean.setViewClass(JstlView.class);
//        bean.setPrefix("/templates/view/");
//        bean.setSuffix(".jsp");
//        return bean;
//    }
}
