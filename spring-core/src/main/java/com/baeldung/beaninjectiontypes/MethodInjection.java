package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.dependencyinjectiontypes.TextFormatter;

import lombok.Getter;

@Component
public class MethodInjection {

    @Getter
    TextFormatter formatter;

    @Autowired
    public void setMethodForTextFormatter(TextFormatter formatter) {
        this.formatter = formatter;
    }

    public String format(String text) {
        return formatter.format(text);
    }

}