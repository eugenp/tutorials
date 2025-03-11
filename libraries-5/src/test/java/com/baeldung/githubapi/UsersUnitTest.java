package com.baeldung.githubapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersUnitTest {
    @Test
    @Disabled
    void whenWeAccessMyself_WeCanQueryUserDetails() throws IOException {
        GitHub gitHub = GitHub.connect();

        GHMyself myself = gitHub.getMyself();
        assertEquals("someone", myself.getLogin());
        assertEquals("someone@example.com", myself.getEmail());
        assertEquals(50, myself.getFollows().size());
    }

    @Test
    void whenWeAccessAnotherUser_WeCanQueryUserDetails() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        GHUser user = gitHub.getUser("eugenp");
        assertEquals("eugenp", user.getLogin());
    }
}
