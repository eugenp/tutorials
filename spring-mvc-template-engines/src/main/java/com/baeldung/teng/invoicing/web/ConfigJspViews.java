package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

public class ConfigJspViews {

    @Bean
    public ViewResolver jstlViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/jsp/");
        bean.setSuffix(".jsp");
        bean.setCache(true);

        return bean;
    }
}
