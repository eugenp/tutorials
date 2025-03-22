package com.baeldung.githubapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientUnitTest {

    @Test
    void whenWeCreateAnAnonynousClient_thenWeCanAccessTheGithubApi() throws IOException {
        GitHub gitHub = GitHub.connectAnonymously();

        String apiUri = gitHub.getApiUrl();
        assertEquals("https://api.github.com", apiUri);
    }

    @Test
    @Disabled // Needs credentials configuring
    void whenWeCreateADefaultClient_thenWeCanAccessTheGithubApi() throws IOException {
        GitHub gitHub = GitHub.connect();

        String apiUri = gitHub.getApiUrl();
        assertEquals("https://api.github.com", apiUri);
    }

    @Test
    @Disabled // Needs credentials configuring
    void whenWeCreateAClientWithProvidedCredentials_thenWeCanAccessTheGithubApi() throws IOException {
        GitHub gitHub = new GitHubBuilder().withPassword("my_user", "my_password").build();

        String apiUri = gitHub.getApiUrl();
        assertEquals("https://api.github.com", apiUri);
    }
}
