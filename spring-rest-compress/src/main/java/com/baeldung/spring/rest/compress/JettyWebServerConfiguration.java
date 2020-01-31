package com.baeldung.spring.rest.compress;

import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure Jetty web server so it handles compressed requests.
 */
@Configuration
public class JettyWebServerConfiguration {

    private static final int MIN_BYTES = 1;

    /**
     * Customise the Jetty web server to automatically decompress requests.
     */
    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory() {

        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        factory.addServerCustomizers(server -> {

            GzipHandler gzipHandler = new GzipHandler();
            // Enable request decompression
            gzipHandler.setInflateBufferSize(MIN_BYTES);
            gzipHandler.setHandler(server.getHandler());

            HandlerCollection handlerCollection = new HandlerCollection(gzipHandler);
            server.setHandler(handlerCollection);
        });

        return factory;
    }

}
