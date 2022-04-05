package com.baeldung.exclude_urls_filter.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogFilter());
        registrationBean.addUrlPatterns("/health", "/faq/*");
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean<HeaderValidatorFilter> headerValidatorFilter() {
        FilterRegistrationBean<HeaderValidatorFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HeaderValidatorFilter());
        registrationBean.addUrlPatterns("*");
        return registrationBean;
    }
}