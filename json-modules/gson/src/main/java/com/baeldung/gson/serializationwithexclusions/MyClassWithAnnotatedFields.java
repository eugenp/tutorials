package com.baeldung.gson.serializationwithexclusions;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MyClassWithAnnotatedFields {

    @Expose
    private long id;
    @Expose
    private String name;
    private String other;
    @Expose
    private MySubClassWithAnnotatedFields subclass;

}
