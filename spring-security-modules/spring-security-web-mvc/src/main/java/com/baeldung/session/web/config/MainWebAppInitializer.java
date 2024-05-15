package com.baeldung.session.web.config;

import jakarta.servlet.ServletContext;

import org.springframework.web.WebApplicationInitializer;

public class MainWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) {
        sc.getSessionCookieConfig().setHttpOnly(true);        
        sc.getSessionCookieConfig().setSecure(true);
    }

}
