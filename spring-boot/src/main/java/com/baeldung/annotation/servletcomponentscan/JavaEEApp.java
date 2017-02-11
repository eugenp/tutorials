package com.baeldung.annotation.servletcomponentscan;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.servlet.ServletContext;

@ApplicationScoped
public class JavaEEApp {

    private ServletContext context;

    /**
     * act as a servletContext provider
     */
    private void setContext(@Observes @Initialized(ApplicationScoped.class) final ServletContext context) {
        if (this.context != null) {
            throw new IllegalStateException("app context started twice");
        }

        this.context = context;
    }

    public ServletContext getContext() {
        return context;
    }

}
