package com.baeldung.reflection.disadvantages.performance;

import org.openjdk.jmh.annotations.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class MethodInvocationBenchmark {

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void directCall() {

        directCall(new Person("John", "Doe", 50));
    }

    @Benchmark
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void reflectiveCall() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        reflectiveCall(new Person("John", "Doe", 50));
    }


    private void directCall(Person person) {

        person.getFirstName();
        person.getLastName();
        person.getAge();
    }

    private void reflectiveCall(Person person) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method getFirstNameMethod = Person.class.getMethod("getFirstName");
        getFirstNameMethod.invoke(person);

        Method getLastNameMethod = Person.class.getMethod("getLastName");
        getLastNameMethod.invoke(person);

        Method getAgeMethod = Person.class.getMethod("getAge");
        getAgeMethod.invoke(person);
    }
}
