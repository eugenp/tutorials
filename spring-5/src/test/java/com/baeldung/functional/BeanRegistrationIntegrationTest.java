package com.baeldung.functional;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.baeldung.Spring5Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5Application.class)
public class BeanRegistrationIntegrationTest {

    @Autowired
    private GenericWebApplicationContext context;

    @Test
    public void whenRegisterBean_thenOk() {
        context.registerBean(MyService.class, () -> new MyService());
        MyService myService = (MyService) context.getBean("com.baeldung.functional.MyService");
        assertTrue(myService.getRandomNumber() < 10);
    }

    @Test
    public void whenRegisterBeanWithName_thenOk() {
        context.registerBean("mySecondService", MyService.class, () -> new MyService());
        MyService mySecondService = (MyService) context.getBean("mySecondService");
        assertTrue(mySecondService.getRandomNumber() < 10);
    }

    @Test
    public void whenRegisterBeanWithCallback_thenOk() {
        context.registerBean("myCallbackService", MyService.class, () -> new MyService(), bd -> bd.setAutowireCandidate(false));
        MyService myCallbackService = (MyService) context.getBean("myCallbackService");
        assertTrue(myCallbackService.getRandomNumber() < 10);
    }

}
