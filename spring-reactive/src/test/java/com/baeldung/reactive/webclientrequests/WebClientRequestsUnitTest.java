package com.baeldung.reactive.webclientrequests;

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

import java.time.Duration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest
public class WebClientRequestsUnitTest {

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
    public void whenCallSimpleURI_thenURIMatched() {
        this.webClient.get()
                .uri("/products")
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products");
    }

    @Test
    public void whenCallSinglePathSegmentUri_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/{id}")
                        .build(2))
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products/2");
    }

    @Test
    public void whenCallMultiplePathSegmentsUri_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/{id}/attributes/{attributeId}")
                        .build(2, 13))
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products/2/attributes/13");
    }

    @Test
    public void whenCallSingleQueryParams_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("name", "AndroidPhone")
                        .queryParam("color", "black")
                        .queryParam("deliveryDate", "13/04/2019")
                        .build())
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13/04/2019");
    }

    @Test
    public void whenCallSingleQueryParamsPlaceholders_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("name", "{title}")
                        .queryParam("color", "{authorId}")
                        .queryParam("deliveryDate", "{date}")
                        .build("AndroidPhone", "black", "13/04/2019"))
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13%2F04%2F2019");
    }

    @Test
    public void whenCallArrayQueryParamsBrackets_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("tag[]", "Snapdragon", "NFC")
                        .build())
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products/?tag%5B%5D=Snapdragon&tag%5B%5D=NFC");
    }


    @Test
    public void whenCallArrayQueryParams_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("category", "Phones", "Tablets")
                        .build())
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products/?category=Phones&category=Tablets");
    }

    @Test
    public void whenCallArrayQueryParamsComma_thenURIMatched() {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/products/")
                        .queryParam("category", String.join(",", "Phones", "Tablets"))
                        .build())
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products/?category=Phones,Tablets");
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
                        .path("/products/")
                        .queryParam("name", "AndroidPhone")
                        .queryParam("color", "black")
                        .queryParam("deliveryDate", "13/04/2019")
                        .build())
                .exchange()
                .block(Duration.ofSeconds(1));
        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13/04/2019");
    }

    private void verifyCalledUrl(String relativeUrl) {
        ClientRequest request = this.argumentCaptor.getValue();
        Assert.assertEquals(String.format("%s%s", BASE_URL, relativeUrl), request.url().toString());
        Mockito.verify(this.exchangeFunction).exchange(request);
        verifyNoMoreInteractions(this.exchangeFunction);
    }
}
