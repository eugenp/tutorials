package com.baeldung.camel.apache.file;

import org.apache.camel.builder.RouteBuilder;

public class MulticastFileRouter extends RouteBuilder {
    private static final String SOURCE_FOLDER = "src/test/source-folder";
    private static final String DESTINATION_FOLDER_WORLD = "src/test/destination-folder-world";
    private static final String DESTINATION_FOLDER_HELLO = "src/test/destination-folder-hello";

    @Override
    public void configure() throws Exception {
        from("file://" + SOURCE_FOLDER + "?delete=true").multicast().to("direct:append", "direct:prepend").end();

        from("direct:append").transform(body().append("World")).to("file://" + DESTINATION_FOLDER_WORLD);

        from("direct:prepend").transform(body().prepend("Hello")).to("file://" + DESTINATION_FOLDER_HELLO);
    }
}
