package com.baeldung.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> loggingFilterRegistration() {
        FilterRegistrationBean<DelegatingFilterProxy> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DelegatingFilterProxy("loggingFilterDelegateProxy"));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }


}
