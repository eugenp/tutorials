package com.baeldung.selfinvocation;

import com.baeldung.Application;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
class MathServiceIntegrationTest {

    @Resource
    private MathService mathService;

    @Test
    void givenCacheableMethod_whenInvokedForSecondTime_thenCounterShouldNotIncrease() {
        AtomicInteger counter = mathService.resetCounter();
        assertThat(mathService.square(2)).isEqualTo(4);
        assertThat(counter.get()).isEqualTo(1);

        mathService.square(2);
        assertThat(counter.get()).isEqualTo(1);

        mathService.square(3);
        assertThat(counter.get()).isEqualTo(2);
    }

    @Test
    void givenCacheableMethod_whenInvokingByInternalCall_thenCacheIsNotTriggered() {
        AtomicInteger counter = mathService.resetCounter();

        assertThat(mathService.sumOfSquareOf2()).isEqualTo(8);
        assertThat(counter.get()).isEqualTo(2);
    }

    @Test
    void givenCacheableMethod_whenInvokingByExternalCall_thenCacheIsTriggered() {
        AtomicInteger counter = mathService.resetCounter();

        assertThat(mathService.sumOfSquareOf3()).isEqualTo(18);
        assertThat(counter.get()).isEqualTo(1);
    }

}