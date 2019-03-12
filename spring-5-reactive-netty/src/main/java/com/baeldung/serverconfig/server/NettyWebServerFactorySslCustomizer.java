package com.baeldung.serverconfig.server;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class NettyWebServerFactorySslCustomizer implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Override
    public void customize(NettyReactiveWebServerFactory serverFactory) {
        Ssl ssl = new Ssl();
        ssl.setEnabled(true);
        ssl.setKeyStore("classpath:sample.jks");
        ssl.setKeyAlias("alias");
        ssl.setKeyPassword("password");
        ssl.setKeyStorePassword("secret");
        serverFactory.setSsl(ssl);
        serverFactory.setPort(8443);
    }
}
