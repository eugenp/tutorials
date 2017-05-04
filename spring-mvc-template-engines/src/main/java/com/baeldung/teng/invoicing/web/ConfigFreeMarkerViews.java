package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
public class ConfigFreeMarkerViews {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfig = new FreeMarkerConfigurer();

        freeMarkerConfig.setTemplateLoaderPath("/WEB-INF/view/ftl/");

        return freeMarkerConfig;
    }

    @Bean
    public ViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver bean = new FreeMarkerViewResolver();

        bean.setContentType("text/html; charset=UTF-8");
        bean.setCache(true);
        bean.setOrder(2);

        return bean;
    }
}
