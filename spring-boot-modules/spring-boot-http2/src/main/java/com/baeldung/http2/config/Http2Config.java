package com.baeldung.http2.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("config-class")
public class Http2Config {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> getWebServerFactoryCustomizer() {
        return factory -> {
            Connector httpConnector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
            httpConnector.setPort(8080);
            factory.addConnectorCustomizers(connector -> connector.addUpgradeProtocol(new Http2Protocol()));
            factory.addAdditionalTomcatConnectors(httpConnector);
        };
    }

}