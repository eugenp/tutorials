package com.baeldung.spring.di.constructor;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.di.config.Config;
import com.baeldung.spring.di.constructor.model.DynaTrade;
import com.baeldung.spring.di.constructor.model.Trade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class DependencyInjectionTest implements ApplicationContextAware
{

    private ApplicationContext applicationContext;

    @Test
    public void constructorInjectionTestJavaConfig() {
        Trade trade = applicationContext.getBean(Trade.class);
        assertNotNull("Failed: Constructor Injection,Bean Reference Injection,Java Config, Stock", trade.getStock());
        assertNotNull("Failed: Constructor Injection,Bean Reference Injection,Java Config, deal", trade.getDeal());
        assertTrue("Failed: Constructor Injection,String Property , Java Config", trade.getStock().getName()
            .equals("Apple"));
    }

    
    @Test
    public void setterInjectionTestJavaConfig() {
        DynaTrade trade = applicationContext.getBean(DynaTrade.class);
        assertNotNull("Failed: Constructor Injection,Bean Reference Injection,Java Config, Stock", trade.getStock());
        assertNotNull("Failed: Constructor Injection,Bean Reference Injection,Java Config, deal", trade.getDeal());
        assertTrue("Failed: Constructor Injection,String Property , Java Config", trade.getStock().getName()
            .equals("Apple"));
    }
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
        
    }
}
