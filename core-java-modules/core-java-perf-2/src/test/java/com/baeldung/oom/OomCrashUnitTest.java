package com.baeldung.oom;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
class OomCrashUnitTest {

    public static final Runnable MEMORY_LEAK = () -> {
        List<byte[]> list = new ArrayList<>();
        while (true) {
            list.add(tenMegabytes());
        }
    };

    @Test
    void givenMemoryLeakCode_whenRunInsideThread_thenMainAppDoestFail() throws InterruptedException {
        Thread memoryLeakThread = new Thread(MEMORY_LEAK);
        memoryLeakThread.start();
        memoryLeakThread.join();
    }

    @Test
    void givenMemoryLeakCode_whenRunSeveralTimesInsideThread_thenMainAppDoestFail() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread memoryLeakThread = new Thread(MEMORY_LEAK);
            memoryLeakThread.start();
            memoryLeakThread.join();
        }
    }

    @Test
    void givenBadExample_whenUseItInProductionCode_thenQuestionedByEmployerAndProbablyFired()
      throws InterruptedException {
        Thread npeThread = new Thread(() -> {
            String nullString = null;
            try {
                nullString.isEmpty();
            } catch (NullPointerException e) {
                throw new OutOfMemoryError(e.getMessage());
            }
        });
        npeThread.start();
        npeThread.join();
    }

    private static byte[] tenMegabytes() {
        return new byte[1024 * 1014 * 10];
    }
}
