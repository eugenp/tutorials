package com.baeldung.lombok.Model;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Student {

    private String name;

    private Double GPA;

    private List<String> enrolledClasses;

    public static class StudentBuilder {
        private List<String> enrolledClasses;

        public StudentBuilder enrolledClasses(List<String> enrolledClasses) {
            this.enrolledClasses = enrolledClasses.stream()
                .map(s -> s.replaceAll(" ", ""))
                .collect(Collectors.toList());
            return this;
        }

    }
    
}
