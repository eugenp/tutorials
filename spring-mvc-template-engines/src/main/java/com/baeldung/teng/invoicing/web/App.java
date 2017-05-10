package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

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
}
