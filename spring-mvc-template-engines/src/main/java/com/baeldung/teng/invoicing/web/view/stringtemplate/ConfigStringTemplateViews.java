package com.baeldung.teng.invoicing.web.view.stringtemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class ConfigStringTemplateViews {

    @Bean
    public ViewResolver stringTemplateViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(StringTemplateView.class);
        bean.setPrefix("/WEB-INF/view/st/");
        bean.setViewNames("*.st", "*.stg"); // we may have multiple InternalResourceViewResolvers...
        bean.setCache(true);
        bean.setOrder(5);

        return bean;
    }
}
