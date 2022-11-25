package com.baeldung.dynamicendpoints.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.dynamicendpoints.filter.DynamicEndpointFilter;
import com.baeldung.dynamicendpoints.config.EndpointRefreshConfigBean;
import com.baeldung.dynamicendpoints.config.EnvironmentConfigBean;

@RestController
@RequestMapping("/")
public class AppController {

    private EndpointRefreshConfigBean endpointRefreshConfigBean;
    private EnvironmentConfigBean environmentConfigBean;

    @Autowired
    public AppController(EndpointRefreshConfigBean endpointRefreshConfigBean, EnvironmentConfigBean environmentConfigBean) {
        this.endpointRefreshConfigBean = endpointRefreshConfigBean;
        this.environmentConfigBean = environmentConfigBean;
    }

    @GetMapping("/foo")
    public ResponseEntity<String> fooHandler() {
        if (endpointRefreshConfigBean.isFoo()) {
            return ResponseEntity.status(200)
                .body("foo");
        } else {
            return ResponseEntity.status(503)
                .body("endpoint is unavailable");
        }
    }

    @GetMapping("/bar1")
    public String bar1Handler() {
        return "bar1";
    }

    @GetMapping("/bar2")
    public String bar2Handler() {
        return "bar2";
    }

    @Bean
    @ConditionalOnBean(EnvironmentConfigBean.class)
    public FilterRegistrationBean<DynamicEndpointFilter> dynamicEndpointFilterFilterRegistrationBean(EnvironmentConfigBean environmentConfigBean) {
        FilterRegistrationBean<DynamicEndpointFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DynamicEndpointFilter(environmentConfigBean.getEnvironment()));
        registrationBean.addUrlPatterns("*");
        return registrationBean;
    }
}
