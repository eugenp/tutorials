package com.baeldung.primitive;

import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;

public abstract class Lookup {
    final protected int s = 50000000;

    abstract public void prepare();

    abstract public void clean();

    abstract public int findPosition();

    abstract public String getSimpleClassName();

    Collection<RunResult> run() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(getSimpleClassName())
                .forks(1)
                .build();
        return new Runner(opt).run();
    }

}
