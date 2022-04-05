package com.baeldung.subflows.subflowmapping;

import java.util.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

@EnableIntegration
@IntegrationComponentScan
public class RouterExample {
    @MessagingGateway
    public interface NumbersClassifier {
        @Gateway(requestChannel = "classify.input")
        void classify(Collection<Integer> numbers);
    }

    @Bean
    QueueChannel multipleofThreeChannel() {
        return new QueueChannel();
    }

    @Bean
    QueueChannel remainderIsOneChannel() {
        return new QueueChannel();
    }

    @Bean
    QueueChannel remainderIsTwoChannel() {
        return new QueueChannel();
    }

    boolean isMultipleOfThree(Integer number) {
        return number % 3 == 0;
    }

    boolean isRemainderOne(Integer number) {
        return number % 3 == 1;
    }

    boolean isRemainderTwo(Integer number) {
        return number % 3 == 2;
    }

    @Bean
    public IntegrationFlow classify() {
        return flow -> flow.split()
            .<Integer, Integer> route(number -> number % 3, 
                mapping -> mapping
                  .channelMapping(0, "multipleofThreeChannel")
                  .subFlowMapping(1, subflow -> subflow.channel("remainderIsOneChannel"))
                  .subFlowMapping(2, subflow -> subflow
                      .<Integer> handle((payload, headers) -> {
                          // do extra work on the payload
                         return payload;
                      }))).channel("remainderIsTwoChannel");
    }

}