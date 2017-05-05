package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfig;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

@Configuration
public class ConfigGroovyMarkupViews {

    @Bean
    public GroovyMarkupConfig groovyMarkupConfig() {
        GroovyMarkupConfigurer groovyMarkupConfig = new GroovyMarkupConfigurer();

        groovyMarkupConfig.setAutoIndent(true);
        groovyMarkupConfig.setAutoNewLine(true);
        groovyMarkupConfig.setResourceLoaderPath("/WEB-INF/view/tpl");

        return groovyMarkupConfig;
    }

    @Bean
    public ViewResolver groovyViewResolver() {
        GroovyMarkupViewResolver bean = new GroovyMarkupViewResolver();

        bean.setContentType("text/html; charset=UTF-8");
        bean.setCache(true);
        bean.setOrder(3);

        return bean;
    }
}
