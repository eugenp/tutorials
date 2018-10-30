package com.baeldung.subflows.subflowchannel;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

@EnableIntegration
@IntegrationComponentScan
public class FilterExample {

    @MessagingGateway
    public interface I {

        @Gateway(requestChannel = "flow.input")
        void flow(Collection<Integer> is);

    }

    @Bean
    DirectChannel multipleof3Channel() {
        return new DirectChannel();
    }

    @Bean
    DirectChannel remainderIs1Channel() {
        return new DirectChannel();
    }

    @Bean
    DirectChannel remainderIs2Channel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow flow() {
        return flow -> flow.split()

            .<Integer> filter(x -> x % 3 == 0, sf -> sf.discardFlow(subf -> subf

                .<Integer> filter(x -> x % 3 == 1, ssf -> ssf.discardChannel("remainderIs2Channel"))
                .channel("remainderIs1Channel")

            ))

            .channel("multipleof3Channel");
    }

    public static void main(String[] args) {
        final ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(FilterExample.class);

        DirectChannel multipleof3Channel = ctx.getBean("multipleof3Channel", DirectChannel.class);
        multipleof3Channel.subscribe(x -> System.out.println("multipleof3Channel: " + x));

        DirectChannel remainderIs1Channel = ctx.getBean("remainderIs1Channel", DirectChannel.class);
        remainderIs1Channel.subscribe(x -> System.out.println("remainderIs1Channel: " + x));

        DirectChannel remainderIs2Channel = ctx.getBean("remainderIs2Channel", DirectChannel.class);
        remainderIs2Channel.subscribe(x -> System.out.println("remainderIs2Channel: " + x));

        ctx.getBean(I.class)
            .flow(Arrays.asList(1, 2, 3, 4, 5, 6));

        ctx.close();

    }
}