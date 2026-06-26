package com.baeldung.virtualthread.synchronize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode({ Mode.AverageTime, Mode.Throughput })
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(value = 2)
@State(Scope.Benchmark)
public class BenchmarkVirtualThread {

    private final CartService cartService = new CartService();

    @Param({ "100", "1000", "10000" })
    private int CONCURRENCY;

    @Benchmark
    public void benchmark() throws InterruptedException, IOException {
        List<Thread> threads = new ArrayList<>();
        IntStream.range(0, CONCURRENCY).forEach(i -> threads.add(Thread.startVirtualThread(() -> cartService.update(UUID.randomUUID()
                .toString(), 2))));

        threads.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}