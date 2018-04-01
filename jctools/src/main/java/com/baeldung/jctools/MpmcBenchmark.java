package com.baeldung.jctools;

import org.jctools.queues.MpmcArrayQueue;
import org.jctools.queues.atomic.MpmcAtomicArrayQueue;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Control;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
public class MpmcBenchmark {

    public static final String GROUP_UNSAFE = "MpmcArrayQueue";
    public static final String GROUP_AFU = "MpmcAtomicArrayQueue";
    public static final String GROUP_JDK = "ArrayBlockingQueue";

    public static final int PRODUCER_THREADS_NUMBER = 32;
    public static final int CONSUMER_THREADS_NUMBER = 32;

    public static final int CAPACITY = 128;

    @State(Scope.Group)
    public static class Mpmc {
        public final Queue<Long> queue = new MpmcArrayQueue<>(CAPACITY);
    }

    @State(Scope.Group)
    public static class MpmcAtomic {
        public final Queue<Long> queue = new MpmcAtomicArrayQueue<>(CAPACITY);
    }

    @State(Scope.Group)
    public static class Jdk {
        public final Queue<Long> queue = new ArrayBlockingQueue<>(CAPACITY);
    }

    @Benchmark
    @Group(GROUP_UNSAFE)
    @GroupThreads(PRODUCER_THREADS_NUMBER)
    public void mpmcWrite(Control control, Mpmc state) {
        write(control, state.queue);
    }

    @Benchmark
    @Group(GROUP_UNSAFE)
    @GroupThreads(CONSUMER_THREADS_NUMBER)
    public void mpmcRead(Control control, Mpmc state) {
        read(control, state.queue);
    }

    @Benchmark
    @Group(GROUP_AFU)
    @GroupThreads(PRODUCER_THREADS_NUMBER)
    public void mpmcAtomicWrite(Control control, MpmcAtomic state) {
        write(control, state.queue);
    }

    @Benchmark
    @Group(GROUP_AFU)
    @GroupThreads(CONSUMER_THREADS_NUMBER)
    public void mpmcAtomicRead(Control control, MpmcAtomic state) {
        read(control, state.queue);
    }

    @Benchmark
    @Group(GROUP_JDK)
    @GroupThreads(PRODUCER_THREADS_NUMBER)
    public void jdkWrite(Control control, Jdk state) {
        write(control, state.queue);
    }

    @Benchmark
    @Group(GROUP_JDK)
    @GroupThreads(CONSUMER_THREADS_NUMBER)
    public void jdkRead(Control control, Jdk state) {
        read(control, state.queue);
    }

    private void write(Control control, Queue<Long> queue) {
        //noinspection StatementWithEmptyBody
        while (!control.stopMeasurement && !queue.offer(1L)) {
            // Is intentionally left blank
        }
    }

    private void read(Control control, Queue<Long> queue) {
        //noinspection StatementWithEmptyBody
        while (!control.stopMeasurement && queue.poll() == null) {
            // Is intentionally left blank
        }
    }
}
