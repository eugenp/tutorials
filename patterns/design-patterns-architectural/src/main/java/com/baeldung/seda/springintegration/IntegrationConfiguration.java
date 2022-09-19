package com.baeldung.seda.springintegration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.MessageGroupProcessor;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@Configuration
@EnableIntegration
public class IntegrationConfiguration {

    private final MessageChannel receiveTextChannel;
    private final MessageChannel splitWordsChannel;
    private final MessageChannel toLowerCaseChannel;
    private final MessageChannel countWordsChannel;
    private final MessageChannel returnResponseChannel;

    private final Function<String, String[]> splitWordsFunction = sentence -> sentence.split(" ");
    private final Function<List<String>, Map<String, Long>> convertArrayListToCountMap = list -> list.stream()
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    private final Function<String, String> toLowerCase = String::toLowerCase;
    private final MessageGroupProcessor buildMessageWithListPayload = messageGroup -> MessageBuilder.withPayload(messageGroup.streamMessages()
        .map(Message::getPayload)
        .collect(Collectors.toList()))
      .build();
    private final ReleaseStrategy listSizeReached = r -> r.size() == r.getSequenceSize();

    public IntegrationConfiguration(MessageChannel receiveTextChannel, MessageChannel splitWordsChannel, MessageChannel toLowerCaseChannel, MessageChannel countWordsChannel, MessageChannel returnResponseChannel) {
        this.receiveTextChannel = receiveTextChannel;
        this.splitWordsChannel = splitWordsChannel;
        this.toLowerCaseChannel = toLowerCaseChannel;
        this.countWordsChannel = countWordsChannel;
        this.returnResponseChannel = returnResponseChannel;
    }

    @Bean
    public IntegrationFlow receiveText() {
        return IntegrationFlows.from(receiveTextChannel)
          .channel(splitWordsChannel)
          .get();
    }

    @Bean
    public IntegrationFlow splitWords() {
        return IntegrationFlows.from(splitWordsChannel)
          .transform(splitWordsFunction)
          .channel(toLowerCaseChannel)
          .get();
    }

    @Bean
    public IntegrationFlow toLowerCase() {
        return IntegrationFlows.from(toLowerCaseChannel)
          .split()
          .transform(toLowerCase)
          .aggregate(aggregatorSpec -> aggregatorSpec.releaseStrategy(listSizeReached)
            .outputProcessor(buildMessageWithListPayload))
          .channel(countWordsChannel)
          .get();
    }

    @Bean
    public IntegrationFlow countWords() {
        return IntegrationFlows.from(countWordsChannel)
          .transform(convertArrayListToCountMap)
          .channel(returnResponseChannel)
          .get();
    }

}
