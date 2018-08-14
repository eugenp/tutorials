package com.reactive.webflux.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class EventClientUnitTest {

    @InjectMocks
    private EventClient eventClient;

    @Test
    public void whenLogEventIsBeingCalled_thenNoInteractionsHappen() {
        WebClient client = mock(WebClient.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        Flux flux = mock(Flux.class);
        CommandLineRunner commandLineRunner = mock(CommandLineRunner.class);

        eventClient.logEvents(client);

        verifyNoMoreInteractions(client);
        verifyNoMoreInteractions(requestHeadersUriSpec);
        verifyNoMoreInteractions(requestHeadersSpec);
        verifyNoMoreInteractions(responseSpec);
        verifyNoMoreInteractions(flux);
        verifyNoMoreInteractions(commandLineRunner);
    }

} 
