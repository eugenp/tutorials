package com.baeldung.test;

import com.baeldung.spring.configuration.AppConfig;
import com.baeldung.spring.service.SpringSuperService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SpringTestInterceptor {
    @Inject
    SpringSuperService springSuperService;

    @Test
    public void givenService_whenServiceAndAspectExecuted_thenOk(){
        String code = "123456";
        String result = springSuperService.getInfoFromService(code);
        Assert.assertEquals(code,result);
    }
}
