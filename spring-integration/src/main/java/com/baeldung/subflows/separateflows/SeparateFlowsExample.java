package com.baeldung.subflows.separateflows;

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
public class SeparateFlowsExample {

    @MessagingGateway
    public interface I {

        @Gateway(requestChannel = "multipleof3Flow.input")
        void multipleof3(Collection<Integer> is);

        @Gateway(requestChannel = "remainderIs1Flow.input")
        void remainderIs1(Collection<Integer> is);

        @Gateway(requestChannel = "remainderIs2Flow.input")
        void remainderIs2(Collection<Integer> is);

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

    public static void main(String[] args) {

        final ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SeparateFlowsExample.class);

        DirectChannel multipleof3Channel = ctx.getBean("multipleof3Channel", DirectChannel.class);
        multipleof3Channel.subscribe(x -> System.out.println("multipleof3Channel: " + x));

        DirectChannel remainderIs1Channel = ctx.getBean("remainderIs1Channel", DirectChannel.class);
        remainderIs1Channel.subscribe(x -> System.out.println("remainderIs1Channel: " + x));

        DirectChannel remainderIs2Channel = ctx.getBean("remainderIs2Channel", DirectChannel.class);
        remainderIs2Channel.subscribe(x -> System.out.println("remainderIs2Channel: " + x));

        ctx.getBean(I.class)
            .multipleof3(Arrays.asList(1, 2, 3, 4, 5, 6));

        ctx.getBean(I.class)
            .remainderIs1(Arrays.asList(1, 2, 3, 4, 5, 6));

        ctx.getBean(I.class)
            .remainderIs2(Arrays.asList(1, 2, 3, 4, 5, 6));

        ctx.close();

    }

}
