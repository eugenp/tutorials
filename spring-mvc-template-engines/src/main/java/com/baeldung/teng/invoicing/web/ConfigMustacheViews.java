package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.mustache.MustacheTemplateFactory;
import org.springframework.web.servlet.view.mustache.MustacheViewResolver;
import org.springframework.web.servlet.view.mustache.jmustache.JMustacheTemplateFactory;
import org.springframework.web.servlet.view.mustache.jmustache.JMustacheTemplateLoader;

@Configuration
public class ConfigMustacheViews {

    @Bean
    public MustacheTemplateFactory templateFactory(ResourceLoader resourceLoader) {
        JMustacheTemplateLoader templateLoader = new JMustacheTemplateLoader();
        templateLoader.setResourceLoader(resourceLoader);

        JMustacheTemplateFactory templateFactory = new JMustacheTemplateFactory();

        templateFactory.setPrefix("/WEB-INF/view/mustache/");
        templateFactory.setTemplateLoader(templateLoader);

        return templateFactory;
    }

    @Bean
    public ViewResolver mustacheViewResolver(MustacheTemplateFactory mustacheTemplateFactory) {
        MustacheViewResolver bean = new MustacheViewResolver();

        bean.setTemplateFactory(mustacheTemplateFactory);
        bean.setContentType("text/html; charset=UTF-8");
        bean.setViewNames("*.mustache");
        bean.setCache(true);
        bean.setOrder(4);

        return bean;
    }
}
