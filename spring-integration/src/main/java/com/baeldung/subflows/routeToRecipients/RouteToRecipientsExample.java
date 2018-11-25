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
            .routeToRecipients(route -> route
                .recipientFlow(subflow -> subflow
                    .<Integer> filter(this::isMultipleOfThree)
                    .channel("multipleofThreeChannel"))
                .<Integer> recipient("remainderIsOneChannel",this::isRemainderOne)
                .<Integer> recipient("remainderIsTwoChannel",this::isRemainderTwo));
    }
   

}