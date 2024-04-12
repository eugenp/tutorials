package com.baeldung.reflection.disadvantages.performance;

import org.openjdk.jmh.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public class InitializationBenchmark {

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void directInit() {

        Person person = new Person("John", "Doe", 50);
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void reflectiveInit() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Constructor<Person> constructor = Person.class.getDeclaredConstructor(String.class, String.class, Integer.class);
        Person person = constructor.newInstance("John", "Doe", 50);
    }
}
