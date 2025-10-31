package com.baeldung.xmlpost.service;


import com.baeldung.xmlpost.model.PaymentRequest;
import com.baeldung.xmlpost.model.PaymentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentService paymentService;

    private final String testUrl = "http://mock-payment-service";

    @Test
    void givenValidPaymentRequest_whenProcessPayment_thenReturnSuccessfulResponse() {
        PaymentRequest request = new PaymentRequest("TXN001", 100.50, "USD", "Jane Doe");
        PaymentResponse expectedResponse = new PaymentResponse(
          "SUCCESS", "Payment processed successfully", "REF12345"
        );

        ResponseEntity<PaymentResponse> mockResponse =
          new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(eq(testUrl), any(HttpEntity.class), eq(PaymentResponse.class)))
          .thenReturn(mockResponse);

        PaymentResponse actualResponse = paymentService.processPayment(request, testUrl);

        assertNotNull(actualResponse);
        assertEquals("SUCCESS", actualResponse.getStatus());
        assertEquals("REF12345", actualResponse.getReferenceNumber());
        assertEquals("Payment processed successfully", actualResponse.getMessage());

        verify(restTemplate).postForEntity(eq(testUrl), any(HttpEntity.class), eq(PaymentResponse.class));
    }

    @Test
    void givenRemoteServiceReturnsBadRequest_whenProcessPayment_thenThrowMeaningfulException() {
        PaymentRequest request = new PaymentRequest("TXN002", 200.0, "EUR", "John Smith");

        when(restTemplate.postForEntity(eq(testUrl), any(HttpEntity.class), eq(PaymentResponse.class)))
          .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid amount"));

        RuntimeException exception = assertThrows(RuntimeException.class,
          () -> paymentService.processPayment(request, testUrl));

        assertTrue(exception.getMessage().contains("Payment processing failed"));
        assertTrue(exception.getMessage().contains("Invalid amount"));
    }

    @Test
    void givenNetworkFailure_whenProcessPayment_thenThrowConnectionErrorException() {
        PaymentRequest request = new PaymentRequest("TXN003", 150.75, "GBP", "Alice Brown");

        when(restTemplate.postForEntity(eq(testUrl), any(HttpEntity.class), eq(PaymentResponse.class)))
          .thenThrow(new RuntimeException("Connection timeout"));

        Exception exception = assertThrows(RuntimeException.class,
          () -> paymentService.processPayment(request, testUrl));

        assertTrue(exception.getMessage().contains("Payment processing failed"));
        assertTrue(exception.getMessage().contains("Connection timeout"));
    }

    @Test
    void givenXmlRequest_whenProcessPayment_thenSetCorrectXmlHttpHeaders() {
        PaymentRequest request = new PaymentRequest("TXN004", 300.0, "CAD", "Bob Wilson");
        PaymentResponse expectedResponse = new PaymentResponse("SUCCESS", "OK", "REF67890");

        when(restTemplate.postForEntity(eq(testUrl), any(HttpEntity.class), eq(PaymentResponse.class)))
          .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        paymentService.processPayment(request, testUrl);

        verify(restTemplate).postForEntity(
          eq(testUrl),
          argThat((HttpEntity<PaymentRequest> entity) -> {
              boolean hasXmlContentType = entity.getHeaders().getContentType()
                .includes(MediaType.APPLICATION_XML);
              boolean acceptsXml = entity.getHeaders().getAccept()
                .contains(MediaType.APPLICATION_XML);
              return hasXmlContentType && acceptsXml;
          }),
          eq(PaymentResponse.class)
        );
    }
}