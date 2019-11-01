package com.baeldung.counter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

import com.baeldung.counter.CounterUtil.MutableInteger;

@Fork(value = 1, warmups = 3)
@BenchmarkMode(Mode.All)
public class CounterStatistics {

    private static final Map<String, Integer> counterMap = new HashMap<>();
    private static final Map<String, MutableInteger> counterWithMutableIntMap = new HashMap<>();
    private static final Map<String, int[]> counterWithIntArrayMap = new HashMap<>();
    private static final Map<String, Long> counterWithLongWrapperMap = new HashMap<>();
    private static final Map<String, Long> counterWithLongWrapperStreamMap = new HashMap<>();
    
    static {
        CounterUtil.COUNTRY_NAMES = new String[10000];
        final String prefix = "NewString"; 
        Random random = new Random();
        for (int i=0; i<10000; i++) {
            CounterUtil.COUNTRY_NAMES[i] = new String(prefix + random.nextInt(1000));
        }
    }

  @Benchmark
    public void wrapperAsCounter() {
        CounterUtil.counterWithWrapperObject(counterMap);
    }

    @Benchmark
    public void lambdaExpressionWithWrapper() {
        CounterUtil.counterWithLambdaAndWrapper(counterWithLongWrapperMap);
    }
    
    @Benchmark
    public void parallelStreamWithWrapper() {
        CounterUtil.counterWithParallelStreamAndWrapper(counterWithLongWrapperStreamMap);
    }

    @Benchmark
    public void mutableIntegerAsCounter() {
        CounterUtil.counterWithMutableInteger(counterWithMutableIntMap);
    }

    @Benchmark
    public void primitiveArrayAsCounter() {
        CounterUtil.counterWithPrimitiveArray(counterWithIntArrayMap);
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
