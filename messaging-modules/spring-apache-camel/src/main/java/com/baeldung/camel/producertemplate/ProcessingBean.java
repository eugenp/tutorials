package com.baeldung.camel.producertemplate;

public class ProcessingBean {

    public String process(String input) {
        return "Bean processed " + input.toUpperCase();
    }

}
