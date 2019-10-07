package com.baeldung.springsecuredsockets.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import java.sql.SQLException;

@Configuration
@EnableWebMvc
@EnableJpaRepositories
@ComponentScan("com.baeldung.springsecuredsockets")
@Import({ SecurityConfig.class, DataStoreConfig.class, SocketBrokerConfig.class, SocketSecurityConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/secured/socket").setViewName("socket");
        registry.addViewController("/secured/success").setViewName("success");
        registry.addViewController("/denied").setViewName("denied");
    }

    @Bean
    public UrlBasedViewResolver viewResolver() {
        final UrlBasedViewResolver bean = new UrlBasedViewResolver();
        bean.setPrefix("/WEB-INF/jsp/");
        bean.setSuffix(".jsp");
        bean.setViewClass(JstlView.class);
        return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/", "/resources/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    // View H2
    @Bean(initMethod="start", destroyMethod="stop")
    public Server h2Console () throws SQLException {
        return Server.createWebServer("-web","-webAllowOthers","-webDaemon","-webPort", "8084");
    }
}