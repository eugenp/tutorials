package com.baeldung.scope;

import com.baeldung.scope.prototype.PrototypeBean;
import com.baeldung.scope.singleton.SingletonLookupBean;
import com.baeldung.scope.singleton.SingletonObjectFactoryBean;
import com.baeldung.scope.singleton.SingletonProviderBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = AppConfig.class)
public class PrototypeBeanInjectionIntegrationTest {

    @Test
    public void givenPrototypeInjection_WhenObjectFactory_ThenNewInstanceReturn() {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SingletonObjectFactoryBean firstContext = context.getBean(SingletonObjectFactoryBean.class);
        SingletonObjectFactoryBean secondContext = context.getBean(SingletonObjectFactoryBean.class);

        PrototypeBean firstInstance = firstContext.getPrototypeInstance();
        PrototypeBean secondInstance = secondContext.getPrototypeInstance();

        Assert.assertTrue("New instance expected", firstInstance != secondInstance);
    }

    @Test
    public void givenPrototypeInjection_WhenLookup_ThenNewInstanceReturn() {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SingletonLookupBean firstContext = context.getBean(SingletonLookupBean.class);
        SingletonLookupBean secondContext = context.getBean(SingletonLookupBean.class);

        PrototypeBean firstInstance = firstContext.getPrototypeBean();
        PrototypeBean secondInstance = secondContext.getPrototypeBean();

        Assert.assertTrue("New instance expected", firstInstance != secondInstance);
    }

    @Test
    public void givenPrototypeInjection_WhenProvider_ThenNewInstanceReturn() {

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        SingletonProviderBean firstContext = context.getBean(SingletonProviderBean.class);
        SingletonProviderBean secondContext = context.getBean(SingletonProviderBean.class);

        PrototypeBean firstInstance = firstContext.getPrototypeInstance();
        PrototypeBean secondInstance = secondContext.getPrototypeInstance();

        Assert.assertTrue("New instance expected", firstInstance != secondInstance);
    }
    
}
