package com.stackify.debug.config;

import com.stackify.debug.JavaRemoteDebuggingApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class WebInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JavaRemoteDebuggingApplication.class);
    }
}
