package com.baeldung.sample.config;
import static org.springframework.http.HttpStatus.*;

import com.baeldung.sample.filters.TrailingSlashRedirectFilterReactive;
import jakarta.servlet.Filter;
import org.springframework.web.server.WebFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.UrlHandlerFilter;
import org.springframework.web.filter.OncePerRequestFilter; 

import com.baeldung.sample.filters.TrailingSlashRedirectFilter;



@Configuration
public class WebConfig {
    @Bean
    public WebFilter trailingSlashRedirectReactiveFilter() {
        return new TrailingSlashRedirectFilterReactive();
    }
    @Bean
    public Filter trailingSlashRedirectFilter() {
        return new TrailingSlashRedirectFilter();
    }
    @Bean
    public FilterRegistrationBean<Filter> trailingSlashFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(trailingSlashRedirectFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> urlHandlerFilterRegistrationBean() {
        FilterRegistrationBean<OncePerRequestFilter> registrationBean = new FilterRegistrationBean<>();
        UrlHandlerFilter urlHandlerFilter = UrlHandlerFilter
		  .trailingSlashHandler("/blog/**").redirect(PERMANENT_REDIRECT)
		  .trailingSlashHandler("/greetings/**").wrapRequest()
		  .build();
        
        registrationBean.setFilter(urlHandlerFilter);
        
        return registrationBean;
    }
}
