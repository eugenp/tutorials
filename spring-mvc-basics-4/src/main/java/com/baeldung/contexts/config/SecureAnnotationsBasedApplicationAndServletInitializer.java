package com.baeldung.contexts.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SecureAnnotationsBasedApplicationAndServletInitializer// extends AbstractDispatcherServletInitializer 
{
    
    //uncomment to run the multiple contexts example
    //@Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }

    //@Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext secureWebAppContext = new AnnotationConfigWebApplicationContext();
        secureWebAppContext.register(SecureWebAppConfig.class);
        return secureWebAppContext;
    }

    //@Override
    protected String[] getServletMappings() {
        return new String[] { "/s/api/*" };
    }


    //@Override
    protected String getServletName() {
        return "secure-dispatcher";
    }

}
