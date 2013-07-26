package org.baeldung.servlet;

import org.baeldung.spring.ClientWebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppNew extends AbstractAnnotationConfigDispatcherServletInitializer {

    public WebAppNew() {
        super();
    }

    // API

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { ClientWebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

}
