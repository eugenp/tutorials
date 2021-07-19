package com.baeldung.blade.sample.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter @Setter private String name;    
    @Getter @Setter private String site;
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}