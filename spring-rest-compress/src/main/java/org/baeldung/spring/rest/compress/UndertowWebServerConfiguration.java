package org.baeldung.spring.rest.compress;

import io.undertow.conduits.GzipStreamSourceConduit;
import io.undertow.server.handlers.encoding.RequestEncodingHandler;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure Undertow web server so it handles compressed requests.
 */
@Configuration
public class UndertowWebServerConfiguration {

    private static final String GZIP_ENCODING = "gzip";

    @Bean
    public UndertowServletWebServerFactory undertowServletWebServerFactory() {

        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addDeploymentInfoCustomizers((deploymentInfo) -> {
            deploymentInfo.addInitialHandlerChainWrapper(handler ->
                    new RequestEncodingHandler(handler).addEncoding(GZIP_ENCODING, GzipStreamSourceConduit.WRAPPER));
        });

        return factory;
    }
}
