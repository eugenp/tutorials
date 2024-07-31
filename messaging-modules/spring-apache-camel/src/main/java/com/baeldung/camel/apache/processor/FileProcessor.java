package com.baeldung.camel.apache.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        String originalFileContent = exchange.getIn().getBody(String.class);
        String upperCaseFileContent = originalFileContent.toUpperCase();
        exchange.getIn().setBody(upperCaseFileContent);
    }

}
