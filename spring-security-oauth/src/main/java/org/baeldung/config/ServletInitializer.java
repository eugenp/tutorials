package org.baeldung.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class ServletInitializer extends AbstractDispatcherServletInitializer {

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(PersistenceJPAConfig.class, WebConfig.class, SecurityConfig.class, ThymeleafConfig.class);
        return context;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.addListener(new SessionListener());
        registerProxyFilter(servletContext, "oauth2ClientContextFilter");
        registerProxyFilter(servletContext, "springSecurityFilterChain");
    }

    private void registerProxyFilter(ServletContext servletContext, String name) {
        final DelegatingFilterProxy filter = new DelegatingFilterProxy(name);
        filter.setContextAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
        servletContext.addFilter(name, filter).addMappingForUrlPatterns(null, false, "/*");
    }

}
