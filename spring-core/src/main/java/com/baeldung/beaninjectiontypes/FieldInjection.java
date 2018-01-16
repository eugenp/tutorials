package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.dependencyinjectiontypes.TextFormatter;

import lombok.Getter;

public class FieldInjection {

    @Autowired
    @Getter
    private TextFormatter formatter;

    public String format(String text) {
        return formatter.format(text);
    }
}
