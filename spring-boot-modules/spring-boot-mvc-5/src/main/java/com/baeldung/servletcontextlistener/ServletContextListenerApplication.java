package com.baeldung.servletcontextlistener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @ServletComponentScan enables scanning for servlet components such as @WebListener, @WebFilter, and @WebServlet.
public class ServletContextListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletContextListenerApplication.class, args);
    }
}
