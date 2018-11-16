package com.baeldung.subflows.discardflow;

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
public class FilterExample {
    @MessagingGateway
    public interface NumbersClassifier {
        @Gateway(requestChannel = "flow.input")
        void flow(Collection<Integer> numbers);
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
    public IntegrationFlow flow() {
        return flow -> flow.split()
            .<Integer> filter(x -> x % 3 == 0, sf -> sf.discardFlow(subf -> subf.<Integer> filter(x -> x % 3 == 1, ssf -> ssf.discardChannel("remainderIs2Channel"))
                .channel("remainderIs1Channel")))
            .channel("multipleof3Channel");
    }

}