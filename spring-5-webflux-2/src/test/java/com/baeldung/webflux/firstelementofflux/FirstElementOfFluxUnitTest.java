package com.baeldung.webflux.firstelementofflux;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.baeldung.webflux.model.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FirstElementOfFluxUnitTest {

    private Payment paymentOf100 = new Payment(100);

    private Flux<Payment> fluxOfThreePayments() {
        return Flux.just(paymentOf100, new Payment(200), new Payment(300));
    }

    @Test
    void givenAPaymentFlux_whenUsingNext_thenGetTheFirstPaymentAsMono() {
        Mono<Payment> firstPayment = fluxOfThreePayments().next();

        StepVerifier.create(firstPayment)
            .expectNext(paymentOf100)
            .verifyComplete();
    }

    @Test
    void givenAnEmptyFlux_whenUsingNext_thenGetAEnEmptyMono() {
        Flux<Payment> emptyFlux = Flux.empty();

        Mono<Payment> firstPayment = emptyFlux.next();

        StepVerifier.create(firstPayment)
            .verifyComplete();
    }

    @Test
    void givenAPaymentFlux_whenUsingTake_thenGetTheFirstPaymentAsFlux() {
        Flux<Payment> firstPaymentFlux = fluxOfThreePayments().take(1);

        StepVerifier.create(firstPaymentFlux)
            .expectNext(paymentOf100)
            .verifyComplete();
    }

    @Test
    void givenAEmptyFlux_whenUsingNext_thenGetAnEmptyFlux() {
        Flux<Payment> emptyFlux = Flux.empty();

        Flux<Payment> firstPaymentFlux = emptyFlux.take(1);

        StepVerifier.create(firstPaymentFlux)
            .verifyComplete();
    }

    @Test
    void givenAPaymentFlux_whenUsingElementAt_thenGetTheFirstPaymentAsMono() {
        Mono<Payment> firstPayment = fluxOfThreePayments().elementAt(0);

        StepVerifier.create(firstPayment)
            .expectNext(paymentOf100)
            .verifyComplete();
    }

    @Test
    void givenAEmptyFlux_whenUsingElementAt_thenGetAnEmptyMono() {
        Flux<Payment> emptyFlux = Flux.empty();

        Mono<Payment> firstPayment = emptyFlux.elementAt(0);

        StepVerifier.create(firstPayment)
            .expectError(IndexOutOfBoundsException.class);
    }

    @Test
    void givenAPaymentFlux_whenUsingBlockFirst_thenGetTheFirstPayment() {
        Payment firstPayment = fluxOfThreePayments().blockFirst();

        assertThat(firstPayment).isEqualTo(paymentOf100);
    }

    @Test
    void givenAEmptyFlux_whenUsingElementAt_thenGetNull() {
        Flux<Payment> emptyFlux = Flux.empty();

        Payment firstPayment = emptyFlux.blockFirst();

        assertThat(firstPayment).isNull();
    }

    @Test
    void givenAPaymentFlux_whenUsingToStream_thenGetTheFirstPaymentAsOptional() {
        Optional<Payment> firstPayment = fluxOfThreePayments().toStream()
            .findFirst();

        assertThat(firstPayment).contains(paymentOf100);
    }

    @Test
    void givenAnEmptyPaymentFlux_whenUsingToStream_thenGetAnEmptyOptional() {
        Flux<Payment> emptyFlux = Flux.empty();

        Optional<Payment> firstPayment = emptyFlux.toStream()
            .findFirst();

        assertThat(firstPayment).isEmpty();
    }
}
