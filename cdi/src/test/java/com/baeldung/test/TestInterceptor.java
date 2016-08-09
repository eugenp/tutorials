package com.baeldung.test;

import com.baeldung.interceptor.Audited;
import com.baeldung.interceptor.AuditedInterceptor;
import com.baeldung.service.SuperService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.util.AnnotationLiteral;

import static javafx.beans.binding.Bindings.select;

public class TestInterceptor {
    Weld weld;
    WeldContainer container;
    @Before
    public void init(){
        weld = new Weld();
        container = weld.initialize();
    }

    @After
    public void shutdown(){
        weld.shutdown();
    }

    @Test
    public void givenTheService_whenMethodAndInterceptorExecuted_thenOK() {
        SuperService superService = container.select(SuperService.class).get();
        String code = "123456";
        superService.deliverService(code);
        Assert.assertTrue(AuditedInterceptor.calledBefore);
        Assert.assertTrue(AuditedInterceptor.calledAfter);
    }
}
