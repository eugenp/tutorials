package com.baeldung.springcloudgateway.customfilters.gatewayapp.filters.factories;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.gateway.filter.factory.SetPathGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.sun.net.httpserver.HttpServer;

import reactor.netty.http.client.HttpClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ScrubResponseGatewayFilterLiveTest {
    
    private static Logger log = LoggerFactory.getLogger(ScrubResponseGatewayFilterLiveTest.class);
    
    private static final String JSON_WITH_FIELDS_TO_SCRUB = "{\r\n"
        + "  \"name\" : \"John Doe\",\r\n"
        + "        \"ssn\"  : \"123-45-9999\",\r\n"
        + "        \"account\" : \"9999888877770000\"\r\n"
        + "}";

    private static final String JSON_WITH_SCRUBBED_FIELDS = "{\r\n"
        + "  \"name\" : \"John Doe\",\r\n"
        + "        \"ssn\"  : \"*\",\r\n"
        + "        \"account\" : \"9999888877770000\"\r\n"
        + "}";
    
    @LocalServerPort
    String port;
    
    @Autowired
    private WebTestClient client;
    
    @Autowired HttpServer server;
    
    @Test
    public void givenRequestToScrubRoute_thenResponseScrubbed() {
                
        client.get()
          .uri("/scrub")
          .accept(MediaType.APPLICATION_JSON)
          .exchange()
          .expectStatus()
            .is2xxSuccessful()
          .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
          .expectBody()
            .json(JSON_WITH_SCRUBBED_FIELDS);
    }
    
    
    @TestConfiguration
    public static class TestRoutesConfiguration {
        
        
        @Bean
        public RouteLocator scrubSsnRoute(RouteLocatorBuilder builder, ScrubResponseGatewayFilterFactory scrubFilterFactory, SetPathGatewayFilterFactory pathFilterFactory, HttpServer server ) {
            
            log.info("[I92] Creating scrubSsnRoute...");
            
            int mockServerPort = server.getAddress().getPort();
            ScrubResponseGatewayFilterFactory.Config config = new ScrubResponseGatewayFilterFactory.Config();
            config.setFields("ssn");
            config.setReplacement("*");
            
            SetPathGatewayFilterFactory.Config pathConfig = new SetPathGatewayFilterFactory.Config();
            pathConfig.setTemplate("/customer");
            
            return builder.routes()
              .route("scrub_ssn",
                 r -> r.path("/scrub")
                   .filters( 
                      f -> f
                        .filter(scrubFilterFactory.apply(config))
                        .filter(pathFilterFactory.apply(pathConfig)))
                   .uri("http://localhost:" + mockServerPort ))
              .build();
        }
        
        @Bean
        public SecurityWebFilterChain testFilterChain(ServerHttpSecurity http ) {
            
            // @formatter:off
            return http.authorizeExchange()
              .anyExchange()
                .permitAll()
              .and()
              .build();
            // @formatter:on
        }
        
        @Bean
        public HttpServer mockServer() throws IOException {
            
            log.info("[I48] Starting mock server...");
            
            HttpServer server = HttpServer.create(new InetSocketAddress(0),0);
            server.createContext("/customer", (exchange) ->  {
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                
                byte[] response = JSON_WITH_FIELDS_TO_SCRUB.getBytes("UTF-8");
                exchange.sendResponseHeaders(200,response.length);
                exchange.getResponseBody().write(response);
            });
            
            server.setExecutor(null);
            server.start();
            
            log.info("[I65] Mock server started. port={}", server.getAddress().getPort());
            return server;
        }
    }
}
