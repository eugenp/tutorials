package com.baeldung.hexagonal.calculator;

import learn.to.tdd.hexagonal.calculator.ports.Reader;
import learn.to.tdd.hexagonal.calculator.ports.Writer;

import java.util.List;

public class Calculator {
    private final Reader reader;
    private final Writer writer;

    public Calculator(final Reader reader, final Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void add() {
        final List<Integer> numbers = reader.read();
        final int sum = add(numbers);
        writer.write(sum);
    }

    private int add(final List<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }
}
