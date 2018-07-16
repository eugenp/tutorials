package com.baeldung.dsl;

import java.io.File;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageHandler;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class JavaDSLFileCopyConfig {

    public final String INPUT_DIR = "source";
    public final String OUTPUT_DIR = "target";
    public final String FILE_PATTERN = "*.jpg";

    @Bean
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource sourceReader = new FileReadingMessageSource();
        sourceReader.setDirectory(new File(INPUT_DIR));
        sourceReader.setFilter(new SimplePatternFileListFilter(FILE_PATTERN));
        return sourceReader;
    }

    @Bean
    public MessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(OUTPUT_DIR));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        return handler;
    }

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows.from(fileReadingMessageSource(), e -> e.poller(Pollers.fixedDelay(10000)))
            .handle(fileWritingMessageHandler())
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
