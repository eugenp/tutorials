package com.baeldung.spring.cloud.aws.sqs;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class CountDownLatchContainer {

    public CountDownLatch stringPayloadLatch = new CountDownLatch(1);
    public CountDownLatch recordPayloadLatch = new CountDownLatch(1);

}
