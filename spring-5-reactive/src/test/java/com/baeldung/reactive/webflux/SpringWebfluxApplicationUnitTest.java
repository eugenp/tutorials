package com.baeldung.reactive.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.reactive.webflux.SimpleWebClient;

import reactor.core.Disposable;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringWebfluxApplicationUnitTest {
	
	@Test
    public void testEventsGenerating()
    {
		SimpleWebClient WebClient = new SimpleWebClient();
        Disposable disposable = WebClient.eventGenerated();
        try {
            Thread.sleep(32000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            disposable.dispose();
        }
    }

}
