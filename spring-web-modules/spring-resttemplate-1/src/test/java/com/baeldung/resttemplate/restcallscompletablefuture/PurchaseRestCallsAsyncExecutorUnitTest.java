package com.baeldung.resttemplate.restcallscompletablefuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class PurchaseRestCallsAsyncExecutorUnitTest {

    @Mock
    private RestTemplate restTemplate;

    private static final String MOCK_BASE_URL = "https://internal-api.com";

    PurchaseRestCallsAsyncExecutor subject;

    @BeforeEach
    void setUp() {
        subject = new PurchaseRestCallsAsyncExecutor(restTemplate);
    }

    @Test
    void givenPurchases_whenAllRestCallsRunsWithoutException_thenUpdateAllPurchasesProperly() {
        Purchase purchase = new Purchase("1", "1", "1");
        List<Purchase> purchases = new ArrayList<>(List.of(purchase));

        when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/users/1"), String.class)).thenReturn(new ResponseEntity<>("User 1", HttpStatus.OK));

        when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/orders/1"), String.class)).thenReturn(new ResponseEntity<>("Order 1", HttpStatus.OK));

        when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/payments/1"), String.class)).thenReturn(new ResponseEntity<>("Payment 1", HttpStatus.OK));

        subject.updatePurchases(purchases);

        assertEquals("User 1", purchases.get(0)
            .getBuyerName());
        assertEquals("Order 1", purchases.get(0)
            .getOrderDescription());
        assertEquals("Payment 1", purchases.get(0)
            .getPaymentDescription());
    }

    @Test
    void givenPurchases_whenExecutorOneRestCallFails_thenHandleExceptionGracefully() {
        Purchase purchase = new Purchase("1", "1", "1");
        List<Purchase> purchases = new ArrayList<>(List.of(purchase));

        when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/users/1"), String.class)).thenReturn(new ResponseEntity<>("User 1", HttpStatus.OK));

        when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/orders/1"), String.class)).thenReturn(new ResponseEntity<>("Order 1", HttpStatus.OK));

        when(restTemplate.getForEntity(MOCK_BASE_URL.concat("/payments/1"), String.class)).thenThrow(IllegalArgumentException.class);

        subject.updatePurchasesHandlingExceptions(purchases);

        assertEquals("User 1", purchases.get(0).getBuyerName());
        assertEquals("Order 1", purchases.get(0).getOrderDescription());
        assertNull(purchases.get(0).getPaymentDescription());
    }
}