package com.baeldung.examples.slack;

import java.util.List;

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationCreateParams;
import com.hubspot.slack.client.methods.params.im.ImOpenParams;
import com.hubspot.slack.client.methods.params.users.UserEmailParams;
import com.hubspot.slack.client.methods.params.users.UsersInfoParams;
import com.hubspot.slack.client.models.response.im.ImOpenResponse;
import com.hubspot.slack.client.models.response.users.UsersInfoResponse;
import com.hubspot.slack.client.models.users.SlackUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlackUserErrorReporter implements ErrorReporter {
    private static final Logger LOG = LoggerFactory.getLogger(SlackUserErrorReporter.class);

    private final SlackClient slackClient;

    private final String user;

    public SlackUserErrorReporter(SlackClient slackClient, String user) {
        this.slackClient = slackClient;
        this.user = user;
    }

    @Override
    public void reportProblem(String problem) {
        LOG.debug("Sending message to user {}: {}", user, problem);
        UsersInfoResponse usersInfoResponse = slackClient
            .lookupUserByEmail(UserEmailParams.builder()
                .setEmail(user)
                .build()
            ).join().unwrapOrElseThrow();

        ImOpenResponse imOpenResponse = slackClient.openIm(ImOpenParams.builder()
            .setUserId(usersInfoResponse.getUser().getId())
            .build()
        ).join().unwrapOrElseThrow();

        imOpenResponse.getChannel().ifPresent(channel -> {
            slackClient.postMessage(
                ChatPostMessageParams.builder()
                    .setText(problem)
                    .setChannelId(channel.getId())
                    .build()
            ).join().unwrapOrElseThrow();
        });
    }
}
