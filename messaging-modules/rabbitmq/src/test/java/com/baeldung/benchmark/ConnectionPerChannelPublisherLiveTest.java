package com.baeldung.benchmark;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class ConnectionPerChannelPublisherLiveTest {

    @Test
    void whenConnectionPerChannel_thenRunBenchmark() throws Exception {
        // host, workerCount, iterations, payloadSize
        Arrays.asList(1,5,10,20,50,100,150).stream()
          .forEach(workers -> {
              ConnectionPerChannelPublisher.main(new String[]{"192.168.99.100", Integer.toString(workers), "1000", "4096"});
          });
    }

}
