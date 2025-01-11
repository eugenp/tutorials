package com.baeldung.objecttostring;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + '}';
    }

    public String toCustomString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
          .append("name", name)
          .append("age", age)
          .toString();
    }
}
