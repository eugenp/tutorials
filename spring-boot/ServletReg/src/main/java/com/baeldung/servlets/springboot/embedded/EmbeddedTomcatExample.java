package com.baeldung.servlets.springboot.embedded;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

<<<<<<< HEAD
@Configuration public class EmbeddedTomcatExample {

    @Bean public EmbeddedServletContainerFactory servletContainer() {
=======
@Configuration
public class EmbeddedTomcatExample {

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
>>>>>>> origin
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        return tomcat;
    }
}
