package com.baeldung.thymeleaf.config;



import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import jakarta.servlet.ServletRegistration;

/**
 * Java configuration file that is used for web application initialization
 */
public class WebApp extends AbstractAnnotationConfigDispatcherServletInitializer {

    public WebApp() {
        super();
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebMVCConfig.class, WebMVCSecurity.class, InitSecurity.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        super.customizeRegistration( registration);
    }
}
