package com.baeldung.spring.pkl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringPklUnitTest {
    Logger logger = LoggerFactory.getLogger(SpringPklUnitTest.class);

    @Autowired
    private GitHubService gitHubService;
    @Autowired
    private JiraService jiraService;

    @Test
    public void whenJiraConfigsDefined_thenLoadFromApplicationPklFile() {
        ToolIntegrationProperties.Connection jiraConnection = jiraService.getJiraConnection();
        ToolIntegrationProperties.Credential jiraCredential = jiraConnection.getCredential();

        assertAll(
            () -> assertEquals("Jira", jiraConnection.getName()),
            () -> assertEquals("https://jira.atlassian.com", jiraConnection.getUrl()),
            () -> assertEquals("jirauser", jiraCredential.getUser()),
            () -> assertEquals("jirapassword", jiraCredential.getPassword()),
            () -> assertDoesNotThrow(jiraService::readIssues)
        );
    }

    @Test
    public void whenGitHubConfigsDefined_thenLoadFromApplicationPklFile() {
        ToolIntegrationProperties.Connection gitHubConnection = gitHubService.getGitConnection();
        ToolIntegrationProperties.Credential gitHubCredential = gitHubConnection.getCredential();

        assertAll(() -> {
            assertEquals("GitHub", gitHubConnection.getName());
            assertEquals("https://api.github.com", gitHubConnection.getUrl());
            assertEquals("gituser", gitHubCredential.getUser());
            assertEquals("gitpassword", gitHubCredential.getPassword());

            assertDoesNotThrow(gitHubService::readIssues);
        });
    }
}
