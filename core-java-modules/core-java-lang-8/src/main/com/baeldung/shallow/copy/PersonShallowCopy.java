package com.baeldung.shallow.copy;

import java.util.List;

import com.baeldung.Hobby;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PersonShallowCopy {

    private String name;
    private int age;
    private List<Hobby> hobbies;

    public PersonShallowCopy(PersonShallowCopy person) {
        this.name = person.name;
        this.age = person.age;
        this.hobbies = person.hobbies;
    }
}
