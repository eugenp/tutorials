package com.baeldung.spring.pkl;

public class GitHubService {
    private final ToolIntegrationProperties.Connection gitConnection;

    public GitHubService(ToolIntegrationProperties.Connection connection) {
        this.gitConnection = connection;
    }

    public String readIssues() {
        return "Reading issues from GitHub URL " + gitConnection.getUrl();
    }

    public ToolIntegrationProperties.Connection getGitConnection() {
        return gitConnection;
    }
}
