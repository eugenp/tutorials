package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class ConfigFreeMarkerViews {

    @Bean
    public FreeMarkerConfig freeMarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfig = new FreeMarkerConfigurer();

        freeMarkerConfig.setTemplateLoaderPath("/WEB-INF/view/ftl/");
        freeMarkerConfig.setDefaultEncoding("UTF-8");

        return freeMarkerConfig;
    }

    @Bean
    public ViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver bean = new FreeMarkerViewResolver();

        bean.setContentType("text/html; charset=UTF-8");
        bean.setSuffix(".ftl");
        bean.setCache(true);

        return bean;
    }
}
