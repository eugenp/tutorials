package com.baeldung.serverconfig.server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;
import reactor.netty.http.server.HttpServer;

@Component
public class NettyWebServerFactoryBootstrapCustomizer implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Override
    public void customize(NettyReactiveWebServerFactory serverFactory) {
        serverFactory.addServerCustomizers(new PortCustomizer(8443));
        serverFactory.addServerCustomizers(new EventLoopNettyCustomizer());
    }

    private static class PortCustomizer implements NettyServerCustomizer {

        private final int port;

        private PortCustomizer(int port) {
            this.port = port;
        }

        @Override
        public HttpServer apply(HttpServer httpServer) {
            return httpServer.port(port);
        }
    }

    private static class EventLoopNettyCustomizer implements NettyServerCustomizer {

        @Override
        public HttpServer apply(HttpServer httpServer) {
            EventLoopGroup parentGroup = new NioEventLoopGroup();
            EventLoopGroup childGroup = new NioEventLoopGroup();
            return httpServer
                    .tcpConfiguration(tcpServer -> tcpServer.bootstrap(
                            serverBootstrap -> serverBootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
                    ));

        }

    }
}
