package com.baeldung.restcallscompletablefuture;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UpdatePurchaseDataTest {

    @Mock private RestTemplate restTemplate;

    UpdatePurchaseData subject = new UpdatePurchaseData(restTemplate);

    @Test
    public void testFetchDataFromApi() {
        RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
        String mockResponse = "Mocked response data";
        String apiUrl = "https://api.example.com/data";

        Mockito.when(restTemplateMock.getForObject(Mockito.anyString(), Mockito.eq(String.class)))
                .thenReturn(mockResponse);

        UpdatePurchaseData restApiClient = new UpdatePurchaseData(restTemplateMock);

        restApiClient.updatePurchases(List.of(new Purchase()));

        assertEquals(mockResponse, responseData);
    }
}