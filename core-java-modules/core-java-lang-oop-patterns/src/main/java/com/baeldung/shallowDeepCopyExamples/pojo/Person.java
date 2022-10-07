package com.baeldung.shallowDeepCopyExamples.pojo;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Person implements Cloneable {
    private String firstName;

    private String lastName;

    private int age;

    private List<String> aliasNames;

    private ContactInfo contactInfo;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
