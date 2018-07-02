package com.baeldung.reactive;





import static org.junit.Assert.assertEquals;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.reactive.model.Foo;

import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FooReactiveControllerIntegrationTest {

	@Autowired
	WebTestClient webTestClient;
	
	@Test
    public void whenGetAllFooEvents_thenCorrect() {
        Foo expectedFooEvent = new Foo(0L, "Foo Event");
        
		FluxExchangeResult<Foo> result =
        		webTestClient.get().uri("/foo-events")
                .accept(MediaType.TEXT_EVENT_STREAM)
        		.exchange()
                .expectStatus().isOk()
                .returnResult(Foo.class);
		
		StepVerifier.create(result.getResponseBody())
		        .expectNext(expectedFooEvent)
		        .expectNextCount(2)
		        .consumeNextWith(event ->
		        		assertEquals(3, event.getId()))
		        .thenCancel()
		        .verify();
    }
}
