package com.baeldung.copy;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Student implements Cloneable, Serializable {
    private int id;
    private int age;
    private Bag bag;

    @Data
    public static class Bag implements Serializable {
        private int size;
    }

    @Override
    public Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}
