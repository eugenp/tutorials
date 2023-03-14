package com.baeldung.camel.apache.file;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class SplitterFileRouter extends RouteBuilder {
    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER = "src/test/destination-folder";

    @Override
    public void configure() throws Exception {

        from("file://" + SOURCE_FOLDER + "?delete=true").split(body().convertToString().tokenize("\n")).setHeader(Exchange.FILE_NAME, body()).to("file://" + DESTINATION_FOLDER);
    }
}
