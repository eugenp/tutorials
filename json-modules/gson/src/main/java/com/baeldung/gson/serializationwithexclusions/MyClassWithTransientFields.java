package com.baeldung.gson.serializationwithexclusions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyClassWithTransientFields {

    private long id;
    private String name;
    private transient String other;
    private MySubClassWithTransientFields subclass;

}
