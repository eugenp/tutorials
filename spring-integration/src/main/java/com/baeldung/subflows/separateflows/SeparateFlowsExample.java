package com.baeldung.subflows.separateflows;

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
public class SeparateFlowsExample {
    @MessagingGateway
    public interface NumbersClassifier {
        @Gateway(requestChannel = "multipleOfThreeFlow.input")
        void multipleofThree(Collection<Integer> numbers);

        @Gateway(requestChannel = "remainderIsOneFlow.input")
        void remainderIsOne(Collection<Integer> numbers);

        @Gateway(requestChannel = "remainderIsTwoFlow.input")
        void remainderIsTwo(Collection<Integer> numbers);
    }
    
    @Bean
    QueueChannel multipleOfThreeChannel() {
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
    public IntegrationFlow multipleOfThreeFlow() {
        return flow -> flow.split()
            .<Integer> filter(this::isMultipleOfThree)
            .channel("multipleOfThreeChannel");
    }

    @Bean
    public IntegrationFlow remainderIsOneFlow() {
        return flow -> flow.split()
            .<Integer> filter(this::isRemainderOne)
            .channel("remainderIsOneChannel");
    }

    @Bean
    public IntegrationFlow remainderIsTwoFlow() {
        return flow -> flow.split()
            .<Integer> filter(this::isRemainderTwo)
            .channel("remainderIsTwoChannel");
    }

}