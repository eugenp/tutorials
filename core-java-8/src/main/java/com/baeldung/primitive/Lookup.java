package com.baeldung.primitive;

import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;

/**
 * An abstract class that is to be extended by the classes that
 * perform lookup in the arrays either of Java primitive types or their wrappers.
 */
public abstract class Lookup {
    /**
     * the array size
     */
    final protected int s = 50000000;

    /**
     * Initialize the array: fill in the array with the same
     * elements except for the last one.
     */
    abstract public void prepare();

    /**
     * Free the array's reference.
     */
    abstract public void clean();

    /**
     * Find the position of the element that is different from the others.
     * By construction, it is the last array element.
     *
     * @return array's last element index
     */
    abstract public int findPosition();

    /**
     * Get the name of the class that extends this one. It is needed in order
     * to set up the benchmark.
     *
     * @return
     */
    abstract public String getSimpleClassName();

    Collection<RunResult> run() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(getSimpleClassName())
                .forks(1)
                .build();
        return new Runner(opt).run();
    }

}
