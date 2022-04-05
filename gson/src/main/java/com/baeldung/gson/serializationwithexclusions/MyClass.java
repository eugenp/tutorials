package com.baeldung.gson.serializationwithexclusions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyClass {
    private long id;
    private String name;
    private String other;
    private MySubClass subclass;
}
