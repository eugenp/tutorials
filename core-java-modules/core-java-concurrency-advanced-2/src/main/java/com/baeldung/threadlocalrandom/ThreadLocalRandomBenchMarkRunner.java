package com.baeldung.threadlocalrandom;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.google.common.collect.ImmutableList;

public class ThreadLocalRandomBenchMarkRunner {

    public static void main(String[] args) throws Exception {

        ChainedOptionsBuilder options = new OptionsBuilder().include(ThreadLocalRandomBenchMarker.class.getSimpleName())
            .forks(1)
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .jvmArgs("-server");

        for (Integer i : ImmutableList.of(1, 2, 8, 32)) {
            new Runner(
                    options
                            .threads(i)
                            .build())
                    .run();
        }
    }
}
