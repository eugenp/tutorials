package com.baeldung.dependencyinjection;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.dependencyinjection.domain.Building;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dependencyinjection.xml")
public class DependencyInjectionTest implements ApplicationContextAware {

    private ApplicationContext beanInjectedContext;
    
    
    @Test
    public void testDependencyInjection(){
       
        Building buildingBean = beanInjectedContext.getBean(Building.class);
        
        assertEquals("Escalator is available and Air-Conditioner is available", buildingBean.toString());
    }
    
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        // TODO Auto-generated method stub
        this.beanInjectedContext = arg0;
    }

}
