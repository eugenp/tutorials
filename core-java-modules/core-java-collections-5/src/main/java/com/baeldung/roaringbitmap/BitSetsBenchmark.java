package com.baeldung.roaringbitmap;

import java.util.BitSet;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.roaringbitmap.RoaringBitmap;

@State(Scope.Thread)
public class BitSetsBenchmark {
    private RoaringBitmap rb1;
    private BitSet bs1;
    private RoaringBitmap rb2;
    private BitSet bs2;
    private final static int SIZE = 10_000_000;

    @Setup
    public void setup() {
        rb1 = new RoaringBitmap();
        bs1 = new BitSet(SIZE);
        rb2 = new RoaringBitmap();
        bs2 = new BitSet(SIZE);
        for (int i = 0; i < SIZE / 2; i++) {
            rb1.add(i);
            bs1.set(i);
        }
        for (int i = SIZE / 2; i < SIZE; i++) {
            rb2.add(i);
            bs2.set(i);
        }
    }

    @Benchmark
    public RoaringBitmap roaringBitmapUnion() {
        return RoaringBitmap.or(rb1, rb2);
    }

    @Benchmark
    public BitSet bitSetUnion() {
        BitSet result = (BitSet) bs1.clone();
        result.or(bs2);
        return result;
    }

    @Benchmark
    public RoaringBitmap roaringBitmapIntersection() {
        return RoaringBitmap.and(rb1, rb2);
    }

    @Benchmark
    public BitSet bitSetIntersection() {
        BitSet result = (BitSet) bs1.clone();
        result.and(bs2);
        return result;
    }

    @Benchmark
    public RoaringBitmap roaringBitmapDifference() {
        return RoaringBitmap.andNot(rb1, rb2);
    }

    @Benchmark
    public BitSet bitSetDifference() {
        BitSet result = (BitSet) bs1.clone();
        result.andNot(bs2);
        return result;
    }

    @Benchmark
    public RoaringBitmap roaringBitmapXOR() {
        return RoaringBitmap.xor(rb1, rb2);
    }

    @Benchmark
    public BitSet bitSetXOR() {
        BitSet result = (BitSet) bs1.clone();
        result.xor(bs2);
        return result;
    }
}