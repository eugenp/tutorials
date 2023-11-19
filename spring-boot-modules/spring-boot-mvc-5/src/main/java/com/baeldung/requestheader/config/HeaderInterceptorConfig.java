package com.baeldung.requestheader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.baeldung.requestheader.interceptor.OperatorHolder;
import com.baeldung.requestheader.interceptor.OperatorInterceptor;

@Configuration
public class HeaderInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(operatorInterceptor());
    }

    @Bean
    public OperatorInterceptor operatorInterceptor() {
        return new OperatorInterceptor(operatorHolder());
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OperatorHolder operatorHolder() {
        return new OperatorHolder();
    }

}
