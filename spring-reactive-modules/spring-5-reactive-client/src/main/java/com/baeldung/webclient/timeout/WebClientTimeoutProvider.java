package com.baeldung.webclient.timeout;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.experimental.UtilityClass;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;
import reactor.netty.transport.ProxyProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class WebClientTimeoutProvider {

    public static WebClient defaultWebClient() {
        HttpClient httpClient = HttpClient.create();

        return buildWebClient(httpClient);
    }

    public WebClient responseTimeoutClient() {
        HttpClient httpClient = HttpClient.create()
          .responseTimeout(Duration.ofSeconds(1));

        return buildWebClient(httpClient);
    }

    public WebClient connectionTimeoutClient() {
        HttpClient httpClient = HttpClient.create()
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);

        return buildWebClient(httpClient);
    }

    public WebClient connectionTimeoutWithKeepAliveClient() {
        HttpClient httpClient = HttpClient.create()
          .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
          .option(ChannelOption.SO_KEEPALIVE, true)
          .option(EpollChannelOption.TCP_KEEPIDLE, 300)
          .option(EpollChannelOption.TCP_KEEPINTVL, 60)
          .option(EpollChannelOption.TCP_KEEPCNT, 8);

        return buildWebClient(httpClient);
    }

    public WebClient readWriteTimeoutClient() {
        HttpClient httpClient = HttpClient.create()
          .doOnConnected(conn -> conn
            .addHandler(new ReadTimeoutHandler(5, TimeUnit.SECONDS))
            .addHandler(new WriteTimeoutHandler(5)));

        return buildWebClient(httpClient);
    }

    public WebClient sslTimeoutClient() {
        HttpClient httpClient = HttpClient.create()
          .secure(spec -> spec
            .sslContext(SslContextBuilder.forClient())
            .defaultConfiguration(SslProvider.DefaultConfigurationType.TCP)
            .handshakeTimeout(Duration.ofSeconds(30))
            .closeNotifyFlushTimeout(Duration.ofSeconds(10))
            .closeNotifyReadTimeout(Duration.ofSeconds(10)));

        return buildWebClient(httpClient);
    }

    public WebClient proxyTimeoutClient() {
        HttpClient httpClient = HttpClient.create()
          .proxy(spec -> spec
            .type(ProxyProvider.Proxy.HTTP)
            .host("http://proxy")
            .port(8080)
            .connectTimeoutMillis(3000));

        return buildWebClient(httpClient);
    }

    private WebClient buildWebClient(HttpClient httpClient) {
        return WebClient.builder()
          .clientConnector(new ReactorClientHttpConnector(httpClient))
          .build();
    }
}
