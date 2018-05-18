package com.baeldung.contexts.config;

<<<<<<< HEAD
import org.springframework.web.context.AbstractContextLoaderInitializer;
=======
>>>>>>> upstream/master
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class AnnotationsBasedApplicationAndServletInitializer extends AbstractDispatcherServletInitializer {

    @Override
    protected WebApplicationContext createRootApplicationContext() {
<<<<<<< HEAD
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootApplicationConfig.class);
        return rootContext;
=======
        //If this is not the only class declaring a root context, we return null because it would clash
        //with other classes, as there can only be a single root context.

        //AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //rootContext.register(RootApplicationConfig.class);
        //return rootContext;
        return null;
>>>>>>> upstream/master
    }

    @Override
    protected WebApplicationContext createServletApplicationContext() {
<<<<<<< HEAD
        AnnotationConfigWebApplicationContext secureWebAppContext = new AnnotationConfigWebApplicationContext();
        secureWebAppContext.register(SecureWebAppConfig.class);
        return secureWebAppContext;
=======
        AnnotationConfigWebApplicationContext normalWebAppContext = new AnnotationConfigWebApplicationContext();
        normalWebAppContext.register(NormalWebAppConfig.class);
        return normalWebAppContext;
>>>>>>> upstream/master
    }

    @Override
    protected String[] getServletMappings() {
<<<<<<< HEAD
        return new String[] { "/s/api/*" };
=======
        return new String[] { "/api/*" };
    }

    @Override
    protected String getServletName() {
        return "normal-dispatcher";
>>>>>>> upstream/master
    }
}
