package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import java.util.HashMap;
import java.util.Map;

public class ConfigVelocityViews {

    @Bean
    public VelocityConfig velocityConfig() {
        VelocityConfigurer velocityConfig = new VelocityConfigurer();

        velocityConfig.setResourceLoaderPath("/WEB-INF/view/vm/");

        Map<String, Object> velocityProps = new HashMap<>();
        velocityProps.put("input.encoding", "UTF-8");
        velocityProps.put("output.encoding", "UTF-8");
        velocityConfig.setVelocityPropertiesMap(velocityProps);
        // OR
        // cfg.setConfigLocation(context.getResource("/WEB-INF/view/vm/velocity.properties"));

        return velocityConfig;
    }

    @Bean
    public ViewResolver velocityViewResolver() {
        VelocityViewResolver bean = new VelocityViewResolver();

        bean.setContentType("text/html; charset=UTF-8");
        bean.setSuffix(".vm");
        bean.setCache(true);

        return bean;
    }
}
