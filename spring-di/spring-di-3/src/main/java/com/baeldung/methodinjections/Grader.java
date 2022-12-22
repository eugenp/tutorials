package com.baeldung.methodinjections;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Grader {

    public String grade(Collection<Integer> marks) {

        boolean result = marks.stream()
            .anyMatch(mark -> mark < 45);
        if (result) {
            return "FAIL";
        }
        return "PASS";
    }
}
