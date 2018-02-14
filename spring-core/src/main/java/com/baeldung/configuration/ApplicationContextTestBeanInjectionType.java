package com.baeldung.configuration;

import com.baeldung.bean.ConstructorInjectionDemoBean;
import com.baeldung.bean.FieldInjectionDemoBean;
import com.baeldung.bean.SetterInjectionDemoBean;
import com.baeldung.dependency.DemoDependency;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by emp304 on 2/14/2018.
 */
@Configuration
public class ApplicationContextTestBeanInjectionType {

    @Bean
    public DemoDependency demoDependency() {
        DemoDependency injectDependency = new DemoDependency();
        return injectDependency;
    }

    @Bean
    public ConstructorInjectionDemoBean constructorInjectionDemoBean() {
        ConstructorInjectionDemoBean constructorInjectionDemoBean = new ConstructorInjectionDemoBean(demoDependency());
        return constructorInjectionDemoBean;
    }

    @Bean
    public FieldInjectionDemoBean fieldInjectionDemoBean() {
        FieldInjectionDemoBean fieldInjectionDemoBean = new FieldInjectionDemoBean(demoDependency());
        return fieldInjectionDemoBean;
    }

    @Bean
    public SetterInjectionDemoBean setterInjectionDemoBean() {
        SetterInjectionDemoBean setterInjectionDemoBean = new SetterInjectionDemoBean();
        return setterInjectionDemoBean;
    }
}
