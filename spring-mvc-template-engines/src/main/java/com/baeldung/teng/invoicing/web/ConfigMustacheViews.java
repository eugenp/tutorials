package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.mustache.MustacheTemplateFactory;
import org.springframework.web.servlet.view.mustache.MustacheViewResolver;
import org.springframework.web.servlet.view.mustache.jmustache.JMustacheTemplateFactory;
import org.springframework.web.servlet.view.mustache.jmustache.JMustacheTemplateLoader;

public class ConfigMustacheViews {

    @Bean
    public MustacheTemplateFactory templateFactory(ResourceLoader resourceLoader) {
        JMustacheTemplateLoader templateLoader = new JMustacheTemplateLoader();
        templateLoader.setResourceLoader(resourceLoader);

        JMustacheTemplateFactory templateFactory = new JMustacheTemplateFactory();

        templateFactory.setPrefix("/WEB-INF/view/mustache/");
        templateFactory.setTemplateLoader(templateLoader);

        // At the time of this writing, the JMustacheTemplateLoader uses the encoding returned by
        // Character.defaultEncoding(); hence, the only easy way I see to control it consistently
        // across platforms is to ensure the -Dfile.encoding=UTF-8 is specified at JVM startup!
        // (the current JMustacheTemplateLoader implementation does not seem to be created with
        // extension in mind or to be customizable to a larger extent!)

        return templateFactory;
    }

    @Bean
    public ViewResolver mustacheViewResolver(MustacheTemplateFactory mustacheTemplateFactory) {
        MustacheViewResolver bean = new MustacheViewResolver();

        bean.setTemplateFactory(mustacheTemplateFactory);
        bean.setContentType("text/html; charset=UTF-8");
        bean.setSuffix(".mustache");
        bean.setCache(true);

        return bean;
    }
}
