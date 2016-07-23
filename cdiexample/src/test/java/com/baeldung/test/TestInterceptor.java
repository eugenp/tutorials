package com.baeldung.test;

import com.baeldung.cdi.service.SuperService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Assert;
import org.junit.Test;

public class TestInterceptor {
    @Test
    public void givenTheService_whenMethodAndInterceptorExecuted_thenOK() {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        SuperService superService = container.instance().select(SuperService.class).get();
        String code = "123456";
        superService.deliverService(code);
        Assert.assertEquals("123456",code);
    }
}
