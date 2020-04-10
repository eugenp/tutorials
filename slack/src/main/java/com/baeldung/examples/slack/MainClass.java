package com.baeldung.examples.slack;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackClientRuntimeConfig;

public class MainClass {
    public static final long MINUTES = 1000 * 60;

    public static void main(String[] args) throws IOException {
        SlackClientRuntimeConfig runtimeConfig = SlackClientRuntimeConfig.builder()
            .setTokenSupplier(() -> "<Your API Token>")
            .build();

        SlackClient slackClient = SlackClientFactory.defaultFactory().build(runtimeConfig);

        ErrorReporter slackChannelErrorReporter = new SlackChannelErrorReporter(slackClient, "general");
        ErrorReporter slackUserErrorReporter = new SlackUserErrorReporter(slackClient, "testuser@baeldung.com");

        ErrorChecker diskSpaceErrorChecker10pct = new DiskSpaceErrorChecker(slackChannelErrorReporter, 0.1);
        ErrorChecker diskSpaceErrorChecker2pct = new DiskSpaceErrorChecker(slackUserErrorReporter, 0.02);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                diskSpaceErrorChecker10pct.check();
            }
        }, 0, 5 * MINUTES);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                diskSpaceErrorChecker2pct.check();
            }
        }, 0, 5 * MINUTES);
    }
}
