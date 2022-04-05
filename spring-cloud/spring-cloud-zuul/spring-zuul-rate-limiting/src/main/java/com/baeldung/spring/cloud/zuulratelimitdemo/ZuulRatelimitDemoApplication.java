package com.baeldung.spring.cloud.zuulratelimitdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class ZuulRatelimitDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulRatelimitDemoApplication.class, args);
    }
}
