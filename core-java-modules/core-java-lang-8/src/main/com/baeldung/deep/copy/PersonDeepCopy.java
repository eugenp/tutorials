package com.baeldung.deep.copy;

import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.Hobby;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PersonDeepCopy {

    private String name;
    private int age;
    private List<Hobby> hobbies;

    public PersonDeepCopy(PersonDeepCopy person) {
        this.name = person.name;
        this.age = person.age;
        this.hobbies = person.hobbies.stream()
            .map(Hobby::new)
            .collect(Collectors.toList());
    }
}
