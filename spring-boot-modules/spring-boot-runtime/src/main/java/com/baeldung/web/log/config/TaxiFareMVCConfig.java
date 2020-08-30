package com.baeldung.web.log.config;

import com.baeldung.web.log.app.TaxiFareRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TaxiFareMVCConfig implements WebMvcConfigurer {

    @Autowired
    private TaxiFareRequestInterceptor taxiFareRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(taxiFareRequestInterceptor).addPathPatterns("/**/taxifare/**/");
    }
}
