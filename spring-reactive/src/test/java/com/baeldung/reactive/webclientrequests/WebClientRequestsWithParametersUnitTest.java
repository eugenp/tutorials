package com.baeldung.reactive.webclientrequests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest
public class WebClientRequestsWithParametersUnitTest {

    private static final String BASE_URL = "https://example.com";

    private WebClient webClient;

    @Captor
    private ArgumentCaptor<ClientRequest> argumentCaptor;

    @Mock
    private ExchangeFunction exchangeFunction;

    @Before
    public void init() {
        ClientResponse mockResponse = mock(ClientResponse.class);
        when(mockResponse.bodyToMono(String.class)).thenReturn(Mono.just("test"));
        when(exchangeFunction.exchange(argumentCaptor.capture())).thenReturn(Mono.just(mockResponse));

        webClient = WebClient
          .builder()
          .baseUrl(BASE_URL)
          .exchangeFunction(exchangeFunction)
          .build();
    }

    @Test
    public void whenCallSimpleURI_thenURIMatched() {
        webClient.get()
          .uri("/products")
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products");
    }

    @Test
    public void whenCallSinglePathSegmentUri_thenURIMatched() {
        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/products/{id}")
            .build(2))
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products/2");
    }

    @Test
    public void whenCallMultiplePathSegmentsUri_thenURIMatched() {
        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/products/{id}/attributes/{attributeId}")
            .build(2, 13))
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products/2/attributes/13");
    }

    @Test
    public void whenCallSingleQueryParams_thenURIMatched() {
        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/products/")
            .queryParam("name", "AndroidPhone")
            .queryParam("color", "black")
            .queryParam("deliveryDate", "13/04/2019")
            .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13/04/2019");
    }

    @Test
    public void whenCallSingleQueryParamsPlaceholders_thenURIMatched() {
        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/products/")
            .queryParam("name", "{title}")
            .queryParam("color", "{authorId}")
            .queryParam("deliveryDate", "{date}")
            .build("AndroidPhone", "black", "13/04/2019"))
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13%2F04%2F2019");
    }

    @Test
    public void whenCallArrayQueryParamsBrackets_thenURIMatched() {
        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/products/")
            .queryParam("tag[]", "Snapdragon", "NFC")
            .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products/?tag%5B%5D=Snapdragon&tag%5B%5D=NFC");
    }

    @Test
    public void whenCallArrayQueryParams_thenURIMatched() {
        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/products/")
            .queryParam("category", "Phones", "Tablets")
            .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products/?category=Phones&category=Tablets");
    }

    @Test
    public void whenCallArrayQueryParamsComma_thenURIMatched() {
        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/products/")
            .queryParam("category", String.join(",", "Phones", "Tablets"))
            .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products/?category=Phones,Tablets");
    }

    @Test
    public void whenUriComponentEncoding_thenQueryParamsNotEscaped() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.URI_COMPONENT);
        webClient = WebClient
          .builder()
          .uriBuilderFactory(factory)
          .baseUrl(BASE_URL)
          .exchangeFunction(exchangeFunction)
          .build();

        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/products/")
            .queryParam("name", "AndroidPhone")
            .queryParam("color", "black")
            .queryParam("deliveryDate", "13/04/2019")
            .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();

        verifyCalledUrl("/products/?name=AndroidPhone&color=black&deliveryDate=13/04/2019");
    }

    private void verifyCalledUrl(String relativeUrl) {
        ClientRequest request = argumentCaptor.getValue();
        assertEquals(String.format("%s%s", BASE_URL, relativeUrl), request.url().toString());

        verify(exchangeFunction).exchange(request);
        verifyNoMoreInteractions(exchangeFunction);
    }
}
