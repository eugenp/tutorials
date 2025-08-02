package com.baeldung.githubapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

public class ClientLiveTest {

    @Test
    void whenWeCreateAnAnonynousClient_thenWeCanAccessTheGithubApi() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        String apiUri = gitHub.getApiUrl();
        assertEquals("https://api.github.com", apiUri);
    }

    @Test
    // Needs credentials configuring in environment variables or ~/.github.
    void whenWeCreateADefaultClient_thenWeCanAccessTheGithubApi() throws IOException {
        GitHub gitHub = GitHub.connect();

        String apiUri = gitHub.getApiUrl();
        assertEquals("https://api.github.com", apiUri);
    }

    @Test
    // Needs credentials configuring
    void whenWeCreateAClientWithProvidedCredentials_thenWeCanAccessTheGithubApi() throws IOException {
        GitHub gitHub = new GitHubBuilder().withPassword("my_user", "my_password").build();

        String apiUri = gitHub.getApiUrl();
        assertEquals("https://api.github.com", apiUri);
    }
}
