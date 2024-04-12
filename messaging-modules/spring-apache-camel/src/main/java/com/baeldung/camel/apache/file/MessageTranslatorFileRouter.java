package com.baeldung.camel.apache.file;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class MessageTranslatorFileRouter extends RouteBuilder {
    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER = "src/test/destination-folder";

    @Override
    public void configure() throws Exception {
        from("file://" + SOURCE_FOLDER + "?delete=true").transform(body().append(header(Exchange.FILE_NAME))).to("file://" + DESTINATION_FOLDER);
    }
}
