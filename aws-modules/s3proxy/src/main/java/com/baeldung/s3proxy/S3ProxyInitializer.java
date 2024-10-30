package com.baeldung.s3proxy;

import org.gaul.s3proxy.S3Proxy;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class S3ProxyInitializer implements ApplicationRunner {

    private final S3Proxy s3Proxy;

    public S3ProxyInitializer(S3Proxy s3Proxy) {
        this.s3Proxy = s3Proxy;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        s3Proxy.start();
    }

}