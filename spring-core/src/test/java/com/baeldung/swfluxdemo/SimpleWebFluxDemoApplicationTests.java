package com.baeldung;


import java.time.Duration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.baeldung.swfluxdemo.controller.SWFluxDemoController;
import com.baeldung.swfluxdemo.service.SWFluxDemoService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes={SWFluxDemoController.class,SWFluxDemoService.class})
@WebFluxTest(controllers=SWFluxDemoController.class)
public class SimpleWebFluxDemoApplicationTests {

    @Autowired
    ApplicationContext context;
	
    @Autowired
	private WebTestClient webClient;
	
	@Autowired
	SWFluxDemoController swFluxDemoController;
	
	@Autowired
	SWFluxDemoService swFluxDemoService;
	

    @Test
    public void get() throws Exception{
    	
    	
    
 FluxExchangeResult<String> result = webClient.get().uri("/").accept(MediaType.TEXT_EVENT_STREAM)
        .exchange()
      .returnResult(String.class);
    	
    Flux<String> intervalString = result.getResponseBody();
    	
        StepVerifier.create(intervalString)        
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(1))
                .expectNextCount(0)
        .thenAwait(Duration.ofSeconds(1))
        .expectNextCount(1)
        .thenAwait(Duration.ofSeconds(1))
        .expectNextCount(2);        
    }
}    

