package com.baeldung.corejava.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person implements Cloneable {

    private int personId;
    private String name;
    private Address address;

    @Override
    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }
}
