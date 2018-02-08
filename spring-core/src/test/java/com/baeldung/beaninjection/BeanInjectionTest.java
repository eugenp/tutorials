package com.baeldung.beaninjection;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.baeldung.beaninjection.constructor.Employee;
import static org.junit.Assert.assertNotNull;

public class BeanInjectionTest {
	
    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid() {
    	ConfigurableApplicationContext context = SpringApplication.run(SpringRunner.class);
    	Employee contructorInjection = (Employee) context.getBean("EmployeeWithConstructorInjection");
    	assertNotNull(contructorInjection.getAddress());
    }
    
    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid() {
    	ConfigurableApplicationContext context = SpringApplication.run(SpringRunner.class);
    	com.baeldung.beaninjection.setter.Employee setterInjection = (com.baeldung.beaninjection.setter.Employee) context.getBean("EmployeeWithSetterInjection");
    	assertNotNull(setterInjection.getAddress());
    }
    
    @Test
    public void givenAutowiredAnnotation_WhenSetUsingReflection_ThenDependencyValid() {
    	ConfigurableApplicationContext context = SpringApplication.run(SpringRunner.class);
    	com.baeldung.beaninjection.field.Employee fieldInjection = (com.baeldung.beaninjection.field.Employee) context.getBean("EmployeeWithFieldInjection");
    	assertNotNull(fieldInjection.getAddress());
    }
	
}
