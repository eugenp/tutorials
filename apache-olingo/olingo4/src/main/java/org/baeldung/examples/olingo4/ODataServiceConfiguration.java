package org.baeldung.examples.olingo4;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServlet;

import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;
import org.apache.olingo.server.api.processor.Processor;
import org.baeldung.examples.olingo4.ODataHttpHandlerFactoryImpl.ODataHttpHandlerFactoryImplBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ODataServiceConfiguration {

    @Bean
    public ServletRegistrationBean<HttpServlet> odataServletRegistration(ODataHttpHandlerFactory factory) {
        ServletRegistrationBean<HttpServlet> srb = 
          new ServletRegistrationBean<>(new ODataServlet(factory), "/odata/*");
        srb.setLoadOnStartup(1);
        return srb;
    }

    @Bean
    public ODataHttpHandlerFactory httpHandlerFactory(CsdlEdmProvider edmProvider, ODataFactory odataFactory, List<Processor> processors) {
        return new ODataHttpHandlerFactoryImplBuilder()
          .edmProvider(edmProvider)
          .odataFactory(odataFactory)
          .processors(processors)
          .build();
    }

}
