package onsuccess;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import reactor.core.publisher.Mono;

class MonoOnSuccessVsOnNexUnitTest {

    @Mock
    PaymentService paymentService = mock(PaymentService.class);

    @BeforeEach
    void beforeEach() {
        reset(paymentService);
    }

    @Test
    void givenAPaymentMono_whenCallingServiceOnNext_thenCallServiceWithPayment() {
        Payment paymentOf100 = new Payment(100);
        Mono<Payment> paymentMono = Mono.just(paymentOf100);

        paymentMono.doOnNext(paymentService::processPayment)
            .block();

        verify(paymentService).processPayment(paymentOf100);
    }

    @Test
    void givenAnEmptyMono_whenCallingServiceOnNext_thenDoNotCallService() {
        Mono<Payment> emptyMono = Mono.empty();

        emptyMono.doOnNext(paymentService::processPayment)
            .block();

        verify(paymentService, never()).processPayment(any());
    }

    @Test
    void givenAPaymentMono_whenCallingServiceOnSuccess_thenCallServiceWithPayment() {
        Payment paymentOf100 = new Payment(100);
        Mono<Payment> paymentMono = Mono.just(paymentOf100);

        paymentMono.doOnSuccess(paymentService::processPayment)
            .block();

        verify(paymentService).processPayment(paymentOf100);
    }

    @Test
    void givenAnEmptyMono_whenCallingServiceOnSuccess_thenCallServiceWithNull() {
        Mono<Payment> emptyMono = Mono.empty();

        emptyMono.doOnSuccess(paymentService::processPayment)
            .block();

        verify(paymentService).processPayment(null);
    }

}