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
@State(Scope.Group)
public class MpmcBenchmark {

    public static final String PARAM_UNSAFE = "MpmcArrayQueue";
    public static final String PARAM_AFU = "MpmcAtomicArrayQueue";
    public static final String PARAM_JDK = "ArrayBlockingQueue";

    public static final int PRODUCER_THREADS_NUMBER = 32;
    public static final int CONSUMER_THREADS_NUMBER = 32;

    public static final String GROUP_NAME = "MyGroup";

    public static final int CAPACITY = 128;

    @Param({PARAM_UNSAFE, PARAM_AFU, PARAM_JDK})
    public volatile String implementation;

    public volatile Queue<Long> queue;

    @Setup(Level.Trial)
    public void setUp() {
        switch (implementation) {
            case PARAM_UNSAFE:
                queue = new MpmcArrayQueue<>(CAPACITY);
                break;
            case PARAM_AFU:
                queue = new MpmcAtomicArrayQueue<>(CAPACITY);
                break;
            case PARAM_JDK:
                queue = new ArrayBlockingQueue<>(CAPACITY);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported implementation " + implementation);
        }
    }


    @Benchmark
    @Group(GROUP_NAME)
    @GroupThreads(PRODUCER_THREADS_NUMBER)
    public void write(Control control) {
        //noinspection StatementWithEmptyBody
        while (!control.stopMeasurement && !queue.offer(1L)) {
            // Is intentionally left blank
        }
    }

    @Benchmark
    @Group(GROUP_NAME)
    @GroupThreads(CONSUMER_THREADS_NUMBER)
    public void read(Control control) {
        //noinspection StatementWithEmptyBody
        while (!control.stopMeasurement && queue.poll() == null) {
            // Is intentionally left blank
        }
    }
}
