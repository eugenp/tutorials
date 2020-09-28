package com.baeldung.demo.conf;

import com.baeldung.demo.listener.CustomListener;
import com.baeldung.demo.servlet.CustomServlet;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextListener;

@Configuration
public class WebConf {

    @Bean
    public ServletRegistrationBean customServletBean() {
        ServletRegistrationBean bean =
                new ServletRegistrationBean(new CustomServlet(), "/servlet");
        return bean;
    }

    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> customListenerBean() {
        ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean();
        bean.setListener(new CustomListener());
        return bean;
    }

}
