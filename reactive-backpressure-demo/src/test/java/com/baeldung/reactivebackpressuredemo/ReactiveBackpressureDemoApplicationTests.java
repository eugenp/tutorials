package com.baeldung.reactivebackpressuredemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.reactivebackpressuredemo.client.FooWebClient;

import reactor.core.Disposable;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.main.web-application-type=reactive", webEnvironment = WebEnvironment.DEFINED_PORT)
public class ReactiveBackpressureDemoApplicationTests {

    @Test
    public void testFooBackPressure() {
        FooWebClient fooWebClient = new FooWebClient();
        Disposable disposable = fooWebClient.newFooResourceDetected();
        try {
            Thread.sleep(32000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

}