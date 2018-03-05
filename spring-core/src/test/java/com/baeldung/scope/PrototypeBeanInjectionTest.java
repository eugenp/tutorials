package com.baeldung.scope;

import com.baeldung.scope.prototype.PrototypeBean;
import com.baeldung.scope.singletone.SingletonLookupBean;
import com.baeldung.scope.singletone.SingletonObjectFactoryBean;
import com.baeldung.scope.singletone.SingletonProviderBean;
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
public class PrototypeBeanInjectionTest {

    @Test
    public void givenPrototypeInjection_WhenObjectFactory_ThenNewInstanceReturn() {

        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        final SingletonObjectFactoryBean firstContext = context.getBean(SingletonObjectFactoryBean.class);
        final SingletonObjectFactoryBean secondContext = context.getBean(SingletonObjectFactoryBean.class);

        PrototypeBean firstInstance = firstContext.getPrototypeInstance();
        PrototypeBean secondInstance = secondContext.getPrototypeInstance();

        Assert.assertTrue("New instance expected", firstInstance != secondInstance);
    }

    @Test
    public void givenPrototypeInjection_WhenLookup_ThenNewInstanceReturn() {

        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        final SingletonLookupBean firstContext = context.getBean(SingletonLookupBean.class);
        final SingletonLookupBean secondContext = context.getBean(SingletonLookupBean.class);

        PrototypeBean firstInstance = firstContext.getPrototypeBean();
        PrototypeBean secondInstance = secondContext.getPrototypeBean();

        Assert.assertTrue("New instance expected", firstInstance != secondInstance);
    }

    @Test
    public void givenPrototypeInjection_WhenProvider_ThenNewInstanceReturn() {

        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        final SingletonProviderBean firstContext = context.getBean(SingletonProviderBean.class);
        final SingletonProviderBean secondContext = context.getBean(SingletonProviderBean.class);

        PrototypeBean firstInstance = firstContext.getPrototypeInstance();
        PrototypeBean secondInstance = secondContext.getPrototypeInstance();

        Assert.assertTrue("New instance expected", firstInstance != secondInstance);
    }
}
