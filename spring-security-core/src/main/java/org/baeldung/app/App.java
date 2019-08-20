package org.baeldung.app;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SpringBootApplication
@EnableJpaRepositories("org.baeldung.repository")
@ComponentScan("org.baeldung")
@EntityScan("org.baeldung.entity")
public class App extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
    public static class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

        @Override
        protected javax.servlet.Filter[] getServletFilters() {
            DelegatingFilterProxy delegateFilterProxy = new DelegatingFilterProxy();
            delegateFilterProxy.setTargetBeanName("loggingFilter");
            return new Filter[] { delegateFilterProxy };
        }

        @Override
        protected Class<?>[] getRootConfigClasses() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected String[] getServletMappings() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
