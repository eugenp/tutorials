package com.baeldung.servletcontextlistener;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomListenerConfiguration {

    @Bean
    public ServletListenerRegistrationBean<CustomLifecycleLoggingListener> lifecycleListener() {
        return new ServletListenerRegistrationBean<>(new CustomLifecycleLoggingListener());
    }
}
