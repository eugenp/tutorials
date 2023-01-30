package com.baeldung.contexts.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ApplicationInitializer //implements WebApplicationInitializer
{
    //uncomment to run the multiple contexts example
    //@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Here, we can define a root context and register servlets, among other things.
        //However, since we've later defined other classes to do the same and they would clash,
        //we leave this commented out.

        //Annotations Context
        //AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        //rootContext.register(RootApplicationConfig.class);
        //Registration
        //servletContext.addListener(new ContextLoaderListener(rootContext));

        //Dispatcher Servlet
        //AnnotationConfigWebApplicationContext normalWebAppContext = new AnnotationConfigWebApplicationContext();
        //normalWebAppContext.register(NormalWebAppConfig.class);
        //ServletRegistration.Dynamic normal = servletContext.addServlet("normal-webapp", new DispatcherServlet(normalWebAppContext));
        //normal.setLoadOnStartup(1);
        //normal.addMapping("/api/*");
    }

}
