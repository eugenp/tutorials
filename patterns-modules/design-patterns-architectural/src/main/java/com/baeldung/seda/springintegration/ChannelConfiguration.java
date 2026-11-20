package com.baeldung.seda.springintegration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ChannelConfiguration {

    private final TaskExecutor receiveTextChannelThreadPool;
    private final TaskExecutor splitWordsChannelThreadPool;
    private final TaskExecutor toLowerCaseChannelThreadPool;
    private final TaskExecutor countWordsChannelThreadPool;
    private final TaskExecutor returnResponseChannelThreadPool;

    public ChannelConfiguration(@Qualifier("receiveTextChannelThreadPool") TaskExecutor receiveTextChannelThreadPool,
        @Qualifier("splitWordsChannelThreadPool") TaskExecutor splitWordsChannelThreadPool,
        @Qualifier("toLowerCaseChannelThreadPool") TaskExecutor toLowerCaseChannelThreadPool,
        @Qualifier("countWordsChannelThreadPool") TaskExecutor countWordsChannelThreadPool,
        @Qualifier("returnResponseChannelThreadPool") TaskExecutor returnResponseChannelThreadPool) {
        this.receiveTextChannelThreadPool = receiveTextChannelThreadPool;
        this.splitWordsChannelThreadPool = splitWordsChannelThreadPool;
        this.toLowerCaseChannelThreadPool = toLowerCaseChannelThreadPool;
        this.countWordsChannelThreadPool = countWordsChannelThreadPool;
        this.returnResponseChannelThreadPool = returnResponseChannelThreadPool;
    }

    @Bean(name = "receiveTextChannel")
    public MessageChannel getReceiveTextChannel() {
        return MessageChannels.executor("receive-text", receiveTextChannelThreadPool)
            .getObject();
    }

    @Bean(name = "splitWordsChannel")
    public MessageChannel getSplitWordsChannel() {
        return MessageChannels.executor("split-words", splitWordsChannelThreadPool)
            .getObject();
    }

    @Bean(name = "toLowerCaseChannel")
    public MessageChannel getToLowerCaseChannel() {
        return MessageChannels.executor("to-lower-case", toLowerCaseChannelThreadPool)
            .getObject();
    }

    @Bean(name = "countWordsChannel")
    public MessageChannel getCountWordsChannel() {
        return MessageChannels.executor("count-words", countWordsChannelThreadPool)
            .getObject();
    }

    @Bean(name = "returnResponseChannel")
    public MessageChannel getReturnResponseChannel() {
        return MessageChannels.executor("return-response", returnResponseChannelThreadPool)
            .getObject();
    }

}
