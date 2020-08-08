package com.baeldung.bitset;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public class VectorOfBitsBenchmark {

    private boolean[] array;
    private BitSet bitSet;

    @Param({"100", "1000", "5000", "50000", "100000", "1000000", "2000000", "3000000", "5000000",
      "7000000", "10000000", "20000000", "30000000", "50000000", "70000000", "1000000000"})
    public int size;

    @Setup(Level.Trial)
    public void setUp() {
        array = new boolean[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = ThreadLocalRandom.current().nextBoolean();
        }

        bitSet = new BitSet(size);
        for (int i = 0; i < size; i++) {
            bitSet.set(i, ThreadLocalRandom.current().nextBoolean());
        }
    }

    @Benchmark
    public boolean getBoolArray() {
        return array[ThreadLocalRandom.current().nextInt(size)];
    }

    @Benchmark
    public boolean getBitSet() {
        return bitSet.get(ThreadLocalRandom.current().nextInt(size));
    }

    @Benchmark
    public void setBoolArray() {
        int index = ThreadLocalRandom.current().nextInt(size);
        array[index] = true;
    }

    @Benchmark
    public void setBitSet() {
        int index = ThreadLocalRandom.current().nextInt(size);
        bitSet.set(index);
    }

    @Benchmark
    public int cardinalityBoolArray() {
        int sum = 0;
        for (boolean b : array) {
            if (b) {
                sum++;
            }
        }

        return sum;
    }

    @Benchmark
    public int cardinalityBitSet() {
        return bitSet.cardinality();
    }
}
