package com.baeldung.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springAsync-config.xml")
public class AsyncWithXMLIntegrationTest {

    @Autowired
    private AsyncComponent asyncAnnotationExample;

    // tests

    @Test
    public void testAsyncAnnotationForMethodsWithVoidReturnType() throws InterruptedException {
        System.out.println("Start - invoking an asynchronous method. " + Thread.currentThread().getName());
        asyncAnnotationExample.asyncMethodWithVoidReturnType();
        Thread.sleep(2000);
        System.out.println("End - invoking an asynchronous method. ");
    }

}
