package com.baeldung.session.web.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

public class MainWebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext sc) throws ServletException {
        sc.getSessionCookieConfig().setHttpOnly(true);        
        sc.getSessionCookieConfig().setSecure(true);
    }

}
