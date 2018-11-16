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
        return f -> f.split()
            .<Integer, Integer> route(p -> p % 3, m -> m.channelMapping(0, "multipleof3Channel")
                .subFlowMapping(1, sf -> sf.channel("remainderIs1Channel"))
                .subFlowMapping(2, sf -> sf.<Integer> handle((payload, headers) -> payload * 1)))
            .channel("remainderIs2Channel");
    }

}