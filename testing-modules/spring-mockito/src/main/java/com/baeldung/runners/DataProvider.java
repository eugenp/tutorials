package com.baeldung.runners;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataProvider {

    private final List<String> memory = Stream.of("baeldung", "java", "dummy").collect(Collectors.toList());

    public Stream<String> getValues() {
        return memory.stream();
    }
}
