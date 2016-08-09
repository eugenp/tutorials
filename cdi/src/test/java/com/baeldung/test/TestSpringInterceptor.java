package com.baeldung.test;

import com.baeldung.spring.configuration.AppConfig;
import com.baeldung.spring.service.SpringSuperService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestSpringInterceptor {
    @Autowired
    SpringSuperService springSuperService;

    @Autowired
    private List<String> accumulator;

    @Test
    public void givenService_whenServiceAndAspectExecuted_thenOk(){
        String code = "123456";
        String result = springSuperService.getInfoFromService(code);
        Assert.assertThat(accumulator.size(), is(2));
        Assert.assertThat(accumulator.get(0),is("Call to getInfoFromService"));
        Assert.assertThat(accumulator.get(1),is("Method called successfully: getInfoFromService"));
    }
}
