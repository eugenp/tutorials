package com.baeldung.constructor;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

class PaymentServiceUnitTest {

    @Test
    void whenConstructorInvokedWithInitializer_ThenMockObjectShouldBeCreated(){
        try(MockedConstruction<PaymentService> mockPaymentService = Mockito.mockConstruction(PaymentService.class,(mock,context)-> when(mock.processPayment()).thenReturn("Credit"))){
            PaymentProcessor paymentProcessor = new PaymentProcessor();
            Assertions.assertEquals(1,mockPaymentService.constructed().size());
            Assertions.assertEquals("Credit", paymentProcessor.processPayment());
        }
    }

    @Test
    void whenConstructorInvokedWithoutInitializer_ThenMockObjectShouldBeCreatedWithNullFields(){
        try(MockedConstruction<PaymentService> mockPaymentService = Mockito.mockConstruction(PaymentService.class)){
            PaymentProcessor paymentProcessor = new PaymentProcessor();
            Assertions.assertEquals(1,mockPaymentService.constructed().size());
            Assertions.assertNull(paymentProcessor.processPayment());
        }
    }

    @Test
    void whenConstructorInvokedWithParameters_ThenMockObjectShouldBeCreated(){
        try(MockedConstruction<PaymentService> mockPaymentService = Mockito.mockConstruction(PaymentService.class,(mock, context) -> when(mock.processPayment()).thenReturn("Credit"))){
            PaymentProcessor paymentProcessor = new PaymentProcessor("Debit");
            Assertions.assertEquals(1,mockPaymentService.constructed().size());
            Assertions.assertEquals("Credit", paymentProcessor.processPayment());
        }
    }

    @Test
    void whenMultipleConstructorsInvoked_ThenMultipleMockObjectsShouldBeCreated(){
        try(MockedConstruction<PaymentService> mockPaymentService = Mockito.mockConstruction(PaymentService.class)){
            PaymentProcessor paymentProcessor = new PaymentProcessor();
            PaymentProcessor secondPaymentProcessor = new PaymentProcessor();
            PaymentProcessor thirdPaymentProcessor = new PaymentProcessor("Debit");

            when(mockPaymentService.constructed().get(0).processPayment()).thenReturn("Credit");
            when(mockPaymentService.constructed().get(1).processPayment()).thenReturn("Online Banking");

            Assertions.assertEquals(3,mockPaymentService.constructed().size());
            Assertions.assertEquals("Credit", paymentProcessor.processPayment());
            Assertions.assertEquals("Online Banking", secondPaymentProcessor.processPayment());
            Assertions.assertNull(thirdPaymentProcessor.processPayment());
        }
    }

    @Test
    void whenDependencyInjectionIsUsed_ThenMockObjectShouldBeCreated(){
        PaymentService mockPaymentService = Mockito.mock(PaymentService.class);
        PaymentProcessor paymentProcessor = new PaymentProcessor(mockPaymentService);
        when(mockPaymentService.processPayment()).thenReturn("Online Banking");
        Assertions.assertEquals("Online Banking", paymentProcessor.processPayment());
    }
}
