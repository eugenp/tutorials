package com.baeldung.gson.serializationwithexclusions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MySubClassWithCustomAnnotatedFields {

    private long id;
    private String description;
    @Exclude 
    private String otherVerboseInfo;
}
