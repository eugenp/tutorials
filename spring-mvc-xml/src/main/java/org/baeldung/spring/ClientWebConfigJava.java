package org.baeldung.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

//@EnableWebMvc
//@Configuration
public class ClientWebConfigJava extends WebMvcConfigurerAdapter {

    public ClientWebConfigJava() {
        super();
    }

    // API

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);

        registry.addViewController("/sample.html");
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");

        return bean;
    }
}