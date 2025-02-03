package com.baeldung.spring.pkl;

public class JiraService {
    private final ToolIntegrationProperties.Connection jiraConnection;

    public JiraService(ToolIntegrationProperties.Connection connection) {
        this.jiraConnection = connection;
    }

    public String readIssues() {
        return "Reading issues from Jira URL " + jiraConnection.getUrl();
    }

    public ToolIntegrationProperties.Connection getJiraConnection() {
        return jiraConnection;
    }
}
