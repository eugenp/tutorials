package com.baeldung.spring.actuator;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Start {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);
    private static final int DEFAULT_PORT = 8080;
    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION = AppConfig.class.getName();
    private static final String MAPPING_URL = "/*";

    /*
     * This application runs a Jetty webcontainer that runs the
     * Spring Dispatcher Servlet.
     */
    public static void main(String[] args) throws Exception {
        new Start().startJetty(getPortFromArgs(args));
    }

    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.parseInt(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        logger.info("No server port configured, falling back to {}", DEFAULT_PORT);
        return DEFAULT_PORT;
    }

    private void startJetty(int port) throws Exception {
        logger.info("Starting server at port {}", port);
        Server server = new Server(port);
        server.setHandler(getServletContextHandler(getContext()));
        server.start();
        logger.info("Server started at port {}", port);
        server.join();
    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }

}
