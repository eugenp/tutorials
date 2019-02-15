package com.baeldung;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SpringBootApplication( exclude = SecurityAutoConfiguration.class)
public class Spring5Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring5Application.class, args);
    }

    public static class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

        @Override
        protected Class<?>[] getRootConfigClasses() {
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

        @Override
        protected javax.servlet.Filter[] getServletFilters() {
            DelegatingFilterProxy delegateFilterProxy = new DelegatingFilterProxy();
            delegateFilterProxy.setTargetBeanName("loggingFilter");
            return new Filter[] { delegateFilterProxy };
        }
    }

}
