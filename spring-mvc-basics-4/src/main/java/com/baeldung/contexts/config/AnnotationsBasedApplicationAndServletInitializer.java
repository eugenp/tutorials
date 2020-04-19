package com.baeldung.contexts.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class AnnotationsBasedApplicationAndServletInitializer //extends AbstractDispatcherServletInitializer 
{

    //uncomment to run the multiple contexts example
    //@Override
    protected WebApplicationContext createRootApplicationContext() {
        //If this is not the only class declaring a root context, we return null because it would clash
        //with other classes, as there can only be a single root context.

        //AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //rootContext.register(RootApplicationConfig.class);
        //return rootContext;
        return null;
    }

    //@Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext normalWebAppContext = new AnnotationConfigWebApplicationContext();
        normalWebAppContext.register(NormalWebAppConfig.class);
        return normalWebAppContext;
    }

    //@Override
    protected String[] getServletMappings() {
        return new String[] { "/api/*" };
    }

    //@Override
    protected String getServletName() {
        return "normal-dispatcher";
    }
}
