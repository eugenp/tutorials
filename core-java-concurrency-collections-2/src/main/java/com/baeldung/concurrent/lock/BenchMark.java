package com.baeldung.concurrent.lock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@Fork(value = 2)
@Warmup(iterations = 3)
public class BenchMark {
    ConcurrentAccessMap accessMyMap;
    
    @Param({"HashMap with Lock", "HashMap with Striped Lock", 
      "ConcurrentHashMap with Lock", "ConcurrentHashMap with Striped Lock"})
    private String type;

    @Setup
    public void setup() {
        switch (type) {
            case "HashMap with Lock":
                accessMyMap = new CoarseGrained(getHashMap());
                break;
            case "ConcurrentHashMap with Lock":
                accessMyMap = new CoarseGrained(getConcurrentHashMap());
                break;
            case "HashMap with Striped Lock":
                accessMyMap = new LockStriped(getHashMap());
                break;
            case "ConcurrentHashMap with Striped Lock":
                accessMyMap = new LockStriped(getConcurrentHashMap());
                break;
        }
    }

    private Map<String, String> getHashMap() {
        return new HashMap<String,String>();
    }

    private Map<String, String> getConcurrentHashMap() {
        return new ConcurrentHashMap<String,String>();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void test() throws InterruptedException {
        accessMyMap.doWork(type);
    }
}
