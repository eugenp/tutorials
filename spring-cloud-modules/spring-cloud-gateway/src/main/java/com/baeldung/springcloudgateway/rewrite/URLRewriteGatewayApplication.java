/**
 * 
 */
package com.baeldung.springcloudgateway.rewrite;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gateway.filter.GatewayFilter;

import reactor.core.publisher.Mono;

/**
 * @author Baeldung
 *
 */
@SpringBootApplication
public class URLRewriteGatewayApplication {
    
    public static void main(String[] args) {
        new SpringApplicationBuilder(URLRewriteGatewayApplication.class)
          .profiles("url-rewrite")
          .run(args);
    }
    
}
