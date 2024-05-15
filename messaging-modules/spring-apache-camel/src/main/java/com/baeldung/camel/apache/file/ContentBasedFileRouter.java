package com.baeldung.camel.apache.file;

import org.apache.camel.builder.RouteBuilder;

public class ContentBasedFileRouter extends RouteBuilder {

    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER_TXT = "src/test/destination-folder-txt";
    private static final String DESTINATION_FOLDER_OTHER = "src/test/destination-folder-other";

    @Override
    public void configure() throws Exception {
        from("file://" + SOURCE_FOLDER + "?delete=true").choice().when(simple("${file:ext} == 'txt'")).to("file://" + DESTINATION_FOLDER_TXT).otherwise().to("file://" + DESTINATION_FOLDER_OTHER);
    }

}
