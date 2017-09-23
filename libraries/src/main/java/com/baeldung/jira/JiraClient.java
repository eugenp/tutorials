package com.baeldung.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JiraClient {

    private static final String USERNAME = "yasin.bhojawala";
    private static final String PASSWORD = "jira@P455";
    private static final String JIRA_URL = "http://jira.baeldung.com";

    public static void main(String[] args) {

        final Issue issue = new JiraClient().getIssue("BAEL-1139");
        System.out.println(issue.getDescription());
    }

    private Issue getIssue(String issueKey) {
        JiraRestClient restClient = getJiraRestClient();
        Issue issue = restClient.getIssueClient().getIssue(issueKey).claim();

        closeRestClient(restClient);
        return issue;
    }

    private JiraRestClient getJiraRestClient() {
        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();

        URI jiraServerUri = getJiraUri();
        return factory
          .createWithBasicHttpAuthentication(jiraServerUri, USERNAME, PASSWORD);
    }

    private URI getJiraUri() {
        URI jiraServerUri = null;
        try {
            jiraServerUri = new URI(JIRA_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jiraServerUri;
    }

    private void closeRestClient(JiraRestClient restClient) {
        try {
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
