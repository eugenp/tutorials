package com.baeldung.teng.invoicing.web.view.stringtemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class ConfigStringTemplateViews {

    @Bean
    public ViewResolver stringTemplateViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(StringTemplateView.class);
        bean.setPrefix("/WEB-INF/view/st/");
        bean.setSuffix(".st");
        bean.setCache(true);

        return bean;
    }
}
