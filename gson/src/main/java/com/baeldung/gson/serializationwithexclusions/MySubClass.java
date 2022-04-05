package com.baeldung.gson.serializationwithexclusions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MySubClass {
    private long id;
    private String description;
    private String otherVerboseInfo;
}
