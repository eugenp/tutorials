package com.baeldung.securerandomtester;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SecureRandomPerformanceTest {

    SecureRandom randomNativePRNGBlocking;
    SecureRandom randomNativePRNGNonBlocking;

    final int NBYTES = 256;
    final int NSAMPLES = 20_000;

    @Setup(Level.Trial)
    public void setup() throws NoSuchAlgorithmException {
        randomNativePRNGBlocking = SecureRandom.getInstance("NativePRNGBlocking");
        randomNativePRNGNonBlocking = SecureRandom.getInstance("NativePRNGNonBlocking");
    }

    @Benchmark
    public void measureTimePRNGBlocking() {
        byte[] randomBytes = new byte[NBYTES];
        for (int i = 0; i < NSAMPLES; i++)
        {
            randomNativePRNGBlocking.nextBytes(randomBytes);          
        }
    }

    @Benchmark
    public void measureTimePRNGNonBlocking() {
        byte[] randomBytes = new byte[NBYTES];
        for (int i = 0; i < NSAMPLES; i++)
        {
            randomNativePRNGNonBlocking.nextBytes(randomBytes);          
        }
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
