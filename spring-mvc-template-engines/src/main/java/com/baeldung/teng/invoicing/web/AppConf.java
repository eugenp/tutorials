package com.baeldung.teng.invoicing.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
public class AppConf extends WebMvcConfigurerAdapter {

    @Value("#{templateEngine}")
    private String templateEngine = "jsp";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        // Configure handling of static resources:
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public ViewResolver jstlViewResolver() {

    @Bean
    public ViewResolver jstlViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/jsp");
        bean.setSuffix(".jsp");
        bean.setCache(true);

        return bean;
    }

    @Bean
    public VelocityConfig velocityConfig() {
        VelocityConfigurer velocityConfig = new VelocityConfigurer();

        Map<String, Object> velocityProps = new HashMap<>();
        velocityProps.put("input.encoding", "UTF-8");
        velocityProps.put("output.encoding", "UTF-8");
        velocityConfig.setVelocityPropertiesMap(velocityProps);

        velocityConfig.setResourceLoaderPath("/WEB-INF/view/vm");

        // If we need to further customize Velocity (the app context should be available though):
        // cfg.setConfigLocation(context.getResource("/WEB-INF/view/vm/velocity.properties"));

        return velocityConfig;
    }

    @Bean
    public ViewResolver velocityViewResolver() {
        VelocityViewResolver bean = new VelocityViewResolver();

        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".vm");
        bean.setCache(true);
        bean.setOrder(1);

        return bean;
    }
}
