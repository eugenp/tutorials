package com.baeldung.etag;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
public class WebConfig {

    // Etags

    // If we're not using Spring Boot we can make use of
    // AbstractAnnotationConfigDispatcherServletInitializer#getServletFilters
    @Bean
    public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
        FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>( new ShallowEtagHeaderFilter());
        filterRegistrationBean.addUrlPatterns("/foos/*");
        filterRegistrationBean.setName("etagFilter");
        return filterRegistrationBean;
    }
    
    // We can also just declare the filter directly
    // @Bean
    // public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
    // return new ShallowEtagHeaderFilter();
    // }
}