package com.baeldung.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.AnotherSampleDAOBean;
import com.baeldung.beaninjection.ExampleDAOBean;
import com.baeldung.beaninjection.ExampleServiceBean;
import com.baeldung.beaninjection.IAnotherSampleDAO;
import com.baeldung.beaninjection.IExampleDAO;
import com.baeldung.beaninjection.IExampleService;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.beaninjection" })
public class ApplicationContextTestBeanInjectionTypes {

    @Bean
    public IExampleDAO exampleDAO() {
        return new ExampleDAOBean("Mandatory DAO Property X");
    }

    @Bean
    public IExampleService exampleServiceBean() {
        ExampleServiceBean serviceBean = new ExampleServiceBean(exampleDAO());
        serviceBean.setAnotherSampleDAO(anotherSampleDAO());
        serviceBean.setPropertyX("Some Service Property X");
        return serviceBean;
    }

    @Bean
    public IAnotherSampleDAO anotherSampleDAO() {
        return new AnotherSampleDAOBean("Mandatory DAO Property Y");
    }

}
