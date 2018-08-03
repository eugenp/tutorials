package com.baeldung.dsl;

import java.io.File;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * JavaDSLFileCopyConfig contains various Integration Flows created from various spring integration components.
 * Activate only one flow at a time by un-commenting @Bean annotation from IntegrationFlow beans. 
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class JavaDSLFileCopyConfig {

    public static final String INPUT_DIR = "source";
    public static final String OUTPUT_DIR = "target";

    @Bean
    public MessageSource<File> anInputSource() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(INPUT_DIR));
        return messageSource;
    }

    @Bean
    public GenericSelector<File> aFilter() {
        return new GenericSelector<File>() {

            @Override
            public boolean accept(File source) {
                return source.getName().endsWith("*.jpg");
            }
        };
    }

    @Bean
    public MessageHandler aServiceActivator() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        return handler;
    }

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from(anInputSource(), configurer -> configurer.poller(Pollers.fixedDelay(10000)))
            .filter(aFilter())
            .handle(aServiceActivator())
            .get();
    }

    // @Bean
    public IntegrationFlow flowWithLambda() {
        return IntegrationFlows.from(anInputSource(), configurer -> configurer.poller(Pollers.fixedDelay(10000)))
            .filter(source -> ((File) source).getName()
                .endsWith(".jpg"))
            .handle(aServiceActivator())
            .get();
    }

    // @Bean
    public IntegrationFlow flowWithPoller() {
        return IntegrationFlows.from(anInputSource(), configurer -> configurer.poller(Pollers.fixedDelay(10000)))
            .filter(aFilter())
            .channel(aChannel())
            .bridge()
            .channel(destinationChannel1())
            .channel(destinationChannel2())
            .handle(aServiceActivator())
            .get();
    }

    @Bean
    public MessageChannel aChannel() {
        return new DirectChannel();
    }

    // @Bean
    public IntegrationFlow flowInitializeFromChannel() {
        return IntegrationFlows.from(aChannel())
            .filter(aFilter())
            .handle(aServiceActivator())
            .get();
    }

    // @Bean
    public IntegrationFlow flowPipeChannel() {
        return IntegrationFlows.from(anInputSource(), configurer -> configurer.poller(Pollers.fixedDelay(10000)))
            .filter(aFilter())
            .channel(aChannel())
            .handle(aServiceActivator())
            .get();
    }

    @Bean
    public MessageChannel sourceChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel destinationChannel1() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel destinationChannel2() {
        return new DirectChannel();
    }

    // @Bean
    public IntegrationFlow flowWithBridge() {
        return IntegrationFlows.from(anInputSource())
            .filter(aFilter())
            .channel(sourceChannel())
            .bridge()
            .channel(destinationChannel1())
            .channel(destinationChannel2())
            .handle(aServiceActivator())
            .get();
    }

    public static void main(final String... args) {
        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(JavaDSLFileCopyConfig.class);
        context.registerShutdownHook();
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a string and press <enter>: ");
        while (true) {
            final String input = scanner.nextLine();
            if ("q".equals(input.trim())) {
                context.close();
                scanner.close();
                break;
            }
        }
        System.exit(0);
    }
}
