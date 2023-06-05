package com.baeldung.sentry.support;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import io.sentry.Sentry;
import io.sentry.SentryLevel;

@WebListener
public class SentryContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        // Besides standard supported locations, let's also allow the DSN to be
        // passed using servlet container managed parameters. This can be useful if your app
        // is hosted in a shared application server.
        ServletContext context = sce.getServletContext();
        String sentryDsn = context.getInitParameter("sentry.dsn");
        
        if ( sentryDsn != null ) {
            context.log("[I21] sentry.dsn init parameter found. Configuring Sentry SDK...");
            Sentry.init(sentryDsn);
        }
        else {
            context.log("[I25] sentry.dsn init parameter not found. Configuring Sentry SDK with defaults");
            Sentry.init();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("[I34] shutting down context");
        Sentry.captureMessage("[I35] contextDestroyed", SentryLevel.INFO);
        Sentry.close();
    }
}
