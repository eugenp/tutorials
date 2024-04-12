package com.baeldung.gson.serializationwithexclusions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyClassWithCustomAnnotatedFields {

    private long id;
    private String name;
    @Exclude
    private String other;
    private MySubClassWithCustomAnnotatedFields subclass;

}
