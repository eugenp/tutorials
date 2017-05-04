package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
public class App extends WebMvcConfigurerAdapter implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext srvContext) {
        final AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.scan("com.baeldung.teng.invoicing");
        srvContext.addListener(new ContextLoaderListener(appContext));

        final ServletRegistration.Dynamic dispatcher =
            srvContext.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);

        // Configure handling of static resources:
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public ViewResolver jstlViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/jsp/");
        bean.setCache(true);

        return bean;
    }

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
        bean.setCache(true);
        bean.setOrder(1);

        return bean;
    }
}
