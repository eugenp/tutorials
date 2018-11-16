package com.baeldung.subflows.routetorecipients;

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
public class RouteToRecipientsExample {
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
            .routeToRecipients(r -> r.<Integer> recipient("multipleof3Channel", p -> p % 3 == 0)// filter
                .<Integer> recipient("remainderIs1Channel", p -> p % 3 == 1)
                .recipientFlow(sf -> sf.<Integer> filter(p -> p % 3 == 2)
                    .channel("remainderIs2Channel")));
    }

}