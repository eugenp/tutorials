package com.baeldung.test.beaninjection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.beaninjection.AnotherSampleDAOBean;
import com.baeldung.beaninjection.ExampleDAOBean;
import com.baeldung.beaninjection.ExampleServiceBean;
import com.baeldung.configuration.ApplicationContextTestBeanInjectionTypes;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContextTestBeanInjectionTypes.class)
public class BeanInjectionJavaConfigTest implements ApplicationContextAware {

    private ApplicationContext beanInjectedContext;

    @Test
    public void testDAOInjectionByJava() {
        ExampleServiceBean serviceBean = beanInjectedContext.getBean(ExampleServiceBean.class);
        assertNotNull("Failed: Constructor Injection,Bean Reference Injection,Java Config, ExampleServiceBean", serviceBean.getExampleDAO());
        assertNotNull("Failed: Constructor Injection,Bean Reference Injection,Java Config, ExampleServiceBean", serviceBean.getAnotherSampleDAO());
        assertTrue("Failed: Constructor Injection,String Property , Java Config", serviceBean.getPropertyX()
            .equals("Some Service Property X"));
    }

    @Test
    public void testPropertyInjectioninDAOByJava() {
        ExampleDAOBean daoBean = beanInjectedContext.getBean(ExampleDAOBean.class);
        assertTrue("Failed: Constructor Injection,String Property , Java Config", daoBean.getPropertyX()
            .equals("Mandatory DAO Property X"));

        AnotherSampleDAOBean anotherDAOBean = beanInjectedContext.getBean(AnotherSampleDAOBean.class);
        assertTrue("Failed: Constructor Injection,String Property , XML Config", anotherDAOBean.getPropertyY()
            .equals("Mandatory DAO Property Y"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub
        this.beanInjectedContext = applicationContext;
    }
}
