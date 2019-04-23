package com.baeldung.spring.webclientrequests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebFluxTest
public class WebClientRequestsTest {

    private static final String BASE_URL = "https://example.com";

    private WebClient webClient;

    @Captor
    private ArgumentCaptor<ClientRequest> argumentCaptor;

    private ExchangeFunction exchangeFunction;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.exchangeFunction = mock(ExchangeFunction.class);
        ClientResponse mockResponse = mock(ClientResponse.class);
        when(this.exchangeFunction.exchange(this.argumentCaptor.capture())).thenReturn(Mono.just(mockResponse));
        this.webClient = WebClient
                .builder()
                .baseUrl(BASE_URL)
                .exchangeFunction(exchangeFunction)
                .build();
    }

    @Test
    public void whenUriComponentEncoding_thenQueryParamsNotEscaped() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
        this.webClient = WebClient
                .builder()
                .uriBuilderFactory(factory)
                .baseUrl(BASE_URL)
                .exchangeFunction(exchangeFunction)
                .build();
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/")
                        .queryParam("title", "Baeldung")
                        .queryParam("authorId", "99")
                        .queryParam("date", "13/04/2019")
                        .build())
                .retrieve();
        verifyCalledUrl("/posts/?title=Baeldung&authorId=99&date=13/04/2019");
    }

    @Test
    public void whenCallSimpleURI_thenURIMatched() {
        this.webClient.get()
                .uri("/posts")
                .retrieve();
        verifyCalledUrl("/posts");
    }

    @Test
    public void whenCallSinglePathSegmentUri_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/{id}")
                        .build(2))
                .retrieve();
        verifyCalledUrl("/posts/2");
    }

    @Test
    public void whenCallMultiplePathSegmentsUri_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/{id}/comments/{commentId}")
                        .build(2, 13))
                .retrieve();
        verifyCalledUrl("/posts/2/comments/13");
    }

    @Test
    public void whenCallSingleQueryParams_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/")
                        .queryParam("title", "Baeldung")
                        .queryParam("authorId", "99")
                        .queryParam("date", "13/04/2019")
                        .build())
                .retrieve();
        verifyCalledUrl("/posts/?title=Baeldung&authorId=99&date=13/04/2019");
    }

    @Test
    public void whenCallSingleQueryParamsPlaceholders_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/")
                        .queryParam("title", "{title}")
                        .queryParam("authorId", "{authorId}")
                        .queryParam("date", "{date}")
                        .build("Baeldung", "99", "13/04/2019"))
                .retrieve();
        verifyCalledUrl("/posts/?title=Baeldung&authorId=99&date=13%2F04%2F2019");
    }

    @Test
    public void whenCallArrayQueryParamsBrackets_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/")
                        .queryParam("tag[]", "Spring", "Kotlin")
                        .build())
                .retrieve();
        verifyCalledUrl("/posts/?tag%5B%5D=Spring&tag%5B%5D=Kotlin");
    }

    @Test
    public void whenCallArrayQueryParams_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/")
                        .queryParam("category", "Web", "Mobile")
                        .build())
                .retrieve();
        verifyCalledUrl("/posts/?category=Web&category=Mobile");
    }

    private void verifyCalledUrl(String relativeUrl) {
        ClientRequest request = this.argumentCaptor.getValue();
        Assert.assertEquals(String.format("%s%s", BASE_URL, relativeUrl), request.url().toString());
        Mockito.verify(this.exchangeFunction).exchange(request);
        verifyNoMoreInteractions(this.exchangeFunction);
    }
}
