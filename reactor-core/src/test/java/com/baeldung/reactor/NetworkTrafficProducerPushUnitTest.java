package com.baeldung.reactor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NetworkTrafficProducerPushUnitTest {
      
    @Test
    public void givenFluxWithAsynchronousPushWithListener_whenListenerIsInvoked_thenItemCollectedByTheSubscriber() throws InterruptedException {
        List<String> elements = new ArrayList<>();

        NetworkTrafficProducerPush trafficProducer = new NetworkTrafficProducerPush();
        trafficProducer.subscribe(elements::add);
        trafficProducer.onPacket("Packet[A18]");
        
        assertThat(elements).containsExactly("Packet[A18]");
    }

}
