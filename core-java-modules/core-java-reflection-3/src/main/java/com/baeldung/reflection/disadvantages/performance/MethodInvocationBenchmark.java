package com.baeldung.reflection.disadvantages.performance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class MethodInvocationBenchmark {

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void directCall(Blackhole blackhole) {

        Person person = new Person("John", "Doe", 50);

        blackhole.consume(person.getFirstName());
        blackhole.consume(person.getLastName());
        blackhole.consume(person.getAge());
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void reflectiveCall(Blackhole blackhole) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        Person person = new Person("John", "Doe", 50);

        Method getFirstNameMethod = Person.class.getMethod("getFirstName");
        blackhole.consume(getFirstNameMethod.invoke(person));

        Method getLastNameMethod = Person.class.getMethod("getLastName");
        blackhole.consume(getLastNameMethod.invoke(person));

        Method getAgeMethod = Person.class.getMethod("getAge");
        blackhole.consume(getAgeMethod.invoke(person));
    }
}
