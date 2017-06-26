package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfig;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

public class ConfigGroovyMarkupViews {

    @Bean
    public GroovyMarkupConfig groovyMarkupConfig() {
        GroovyMarkupConfigurer groovyMarkupConfig = new GroovyMarkupConfigurer() {

            @Override
            public void afterPropertiesSet() throws Exception {
                super.afterPropertiesSet();

                // We write our templates using UTF-8
                getTemplateEngine().getCompilerConfiguration().setSourceEncoding("UTF-8");
            }
        };

        groovyMarkupConfig.setAutoIndent(true);
        groovyMarkupConfig.setAutoNewLine(true);
        groovyMarkupConfig.setAutoIndentString("  ");
        groovyMarkupConfig.setResourceLoaderPath("/WEB-INF/view/tpl");

        return groovyMarkupConfig;
    }

    @Bean
    public ViewResolver groovyViewResolver() {
        GroovyMarkupViewResolver bean = new GroovyMarkupViewResolver();

        bean.setContentType("text/html; charset=UTF-8");
        bean.setSuffix(".tpl");
        bean.setCache(true);

        return bean;
    }
}
