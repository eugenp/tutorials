package com.baeldung.selfinvocation;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = LoadTimeWeavingApplication.class)
class LoadTimeWeavingIntegrationTest {

    @Resource
    private MathService mathService;

    @Test
    void givenCacheableMethod_whenInvokingByInternalCall_thenCacheIsTriggered() {
        AtomicInteger counter = mathService.resetCounter();

        assertThat(mathService.sumOfSquareOf2()).isEqualTo(8);
        assertThat(counter.get()).isEqualTo(1);
    }

}