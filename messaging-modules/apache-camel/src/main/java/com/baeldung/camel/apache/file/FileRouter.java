package com.baeldung.camel.apache.file;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouter extends RouteBuilder {

    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER = "src/test/destination-folder";

    @Override
    public void configure() throws Exception {
        from("file://" + SOURCE_FOLDER + "?delete=true").process(new FileProcessor()).to("file://" + DESTINATION_FOLDER);
    }

}
