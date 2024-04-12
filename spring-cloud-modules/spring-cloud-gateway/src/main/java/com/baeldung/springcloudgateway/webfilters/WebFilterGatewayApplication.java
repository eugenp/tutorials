package com.baeldung.springcloudgateway.webfilters;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebFilterGatewayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebFilterGatewayApplication.class)
          .profiles("url-rewrite")
          .run(args);
    }

}