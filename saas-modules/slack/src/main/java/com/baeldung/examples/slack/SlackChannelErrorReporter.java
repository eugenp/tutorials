package com.baeldung.examples.slack;

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlackChannelErrorReporter implements ErrorReporter {
    private static final Logger LOG = LoggerFactory.getLogger(SlackChannelErrorReporter.class);

    private final SlackClient slackClient;

    private final String channel;

    public SlackChannelErrorReporter(SlackClient slackClient, String channel) {
        this.slackClient = slackClient;
        this.channel = channel;
    }

    @Override
    public void reportProblem(String problem) {
        LOG.debug("Sending message to channel {}: {}", channel, problem);
        slackClient.postMessage(
            ChatPostMessageParams.builder()
                .setText(problem)
                .setChannelId(channel)
                .build()
        ).join().unwrapOrElseThrow();
    }
}
