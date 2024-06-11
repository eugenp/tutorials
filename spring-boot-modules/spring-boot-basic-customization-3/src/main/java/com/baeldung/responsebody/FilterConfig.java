package com.baeldung.responsebody;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MD5Filter> loggingFilter() {
        FilterRegistrationBean<MD5Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MD5Filter());
        return registrationBean;
    }
}