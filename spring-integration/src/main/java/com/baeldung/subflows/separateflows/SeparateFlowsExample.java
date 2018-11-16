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
        @Gateway(requestChannel = "multipleof3Flow.input")
        void multipleof3(Collection<Integer> numbers);

        @Gateway(requestChannel = "remainderIs1Flow.input")
        void remainderIs1(Collection<Integer> numbers);

        @Gateway(requestChannel = "remainderIs2Flow.input")
        void remainderIs2(Collection<Integer> numbers);
    }

    @Bean
    QueueChannel multipleof3Channel() {
        return new QueueChannel();
    }

    @Bean
    QueueChannel remainderIs1Channel() {
        return new QueueChannel();
    }

    @Bean
    QueueChannel remainderIs2Channel() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow multipleof3Flow() {
        return f -> f.split()
            .<Integer> filter(p -> p % 3 == 0)
            .channel("multipleof3Channel");
    }

    @Bean
    public IntegrationFlow remainderIs1Flow() {
        return f -> f.split()
            .<Integer> filter(p -> p % 3 == 1)
            .channel("remainderIs1Channel");
    }

    @Bean
    public IntegrationFlow remainderIs2Flow() {
        return f -> f.split()
            .<Integer> filter(p -> p % 3 == 2)
            .channel("remainderIs2Channel");
    }
   
}