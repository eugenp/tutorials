package com.baeldung.threadlocalrandom;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class ThreadLocalRandomBenchMarkRunner {

    public static void main(String[] args) throws Exception {

        Options options = new OptionsBuilder().include(ThreadLocalRandomBenchMarker.class.getSimpleName())
            .threads(1)
            .forks(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .jvmArgs("-server")
            .build();

        new Runner(options).run();

    }
}
