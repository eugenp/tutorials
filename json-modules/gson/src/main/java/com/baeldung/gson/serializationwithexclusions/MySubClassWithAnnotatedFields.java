package com.baeldung.gson.serializationwithexclusions;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MySubClassWithAnnotatedFields {

    @Expose private long id;
    @Expose private String description;
    private String otherVerboseInfo;
}
