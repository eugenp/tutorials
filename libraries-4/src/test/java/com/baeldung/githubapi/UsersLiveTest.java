package com.baeldung.githubapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersLiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(UsersLiveTest.class);

    @Test
    // Needs credentials configuring in environment variables or ~/.github.
    void whenWeAccessMyself_thenWeCanQueryUserDetails() throws IOException {
        GitHub gitHub = GitHub.connect();

        GHMyself myself = gitHub.getMyself();
        LOG.info("Current users username: {}", myself.getLogin());
        LOG.info("Current users email: {}", myself.getEmail());
    }

    @Test
    void whenWeAccessAnotherUser_thenWeCanQueryUserDetails() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHUser user = gitHub.getUser("eugenp");
        assertEquals("eugenp", user.getLogin());
    }
}
