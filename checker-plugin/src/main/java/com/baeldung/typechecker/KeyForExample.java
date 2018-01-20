package com.baeldung.typechecker;

import org.checkerframework.checker.nullness.qual.KeyFor;

import java.util.HashMap;
import java.util.Map;

public class KeyForExample {

    private final @KeyFor("paramToValue") String NAME = "Name";
    private final @KeyFor("paramToValue") String URL = "url";

    private Map<String, String> paramToValue = new HashMap<>();

    KeyForExample(){
        paramToValue.put(URL, "http://1.2.3.4");
    }

    String getMaker(@KeyFor("this.paramToValue") Object model){
        return paramToValue.get(model);
    }

}
