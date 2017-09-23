package com.baeldung.jira;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class JiraClient_bak {

    public static void main(String[] args) {
        URI jiraServerUri = getJiraUri();
        final JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        final JiraRestClient restClient = factory
          .createWithBasicHttpAuthentication(jiraServerUri, "yasin.bhojawala", "jira@P455");
        try {
            final Promise<Issue> issuePromise = restClient
              .getIssueClient()
              .getIssue("BAEL-1139");
            final Issue issue = issuePromise
              .claim();
            System.out.println(issue.getDescription());
            System.out.println("Initial votes");
            System.out.println(issue.getVotes().getVotes());

            restClient.getIssueClient().unvote(issue.getVotesUri()).claim();
            System.out.println("after unvote");
            System.out.println(issuePromise.claim().getVotes().getVotes());

            restClient.getIssueClient().vote(issue.getVotesUri()).claim();
            System.out.println("after upvote");
            System.out.println(issuePromise.claim().getVotes().getVotes());

            restClient.getIssueClient().unvote(issue.getVotesUri()).claim();
            System.out.println("after unvote");
            System.out.println(issuePromise.claim().getVotes().getVotes());

//            restClient.getIssueClient().addComment(issue.getCommentsUri(), Comment.valueOf("Hello1"));
//            issuePromise.claim().getComments().forEach(c -> System.out.println(c.getBody()));
        }
        finally {
            closeRestClient(restClient);
        }
    }

    private static URI getJiraUri() {
        URI jiraServerUri = null;
        try {
            jiraServerUri = new URI("http://jira.baeldung.com");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return jiraServerUri;
    }

    private static void closeRestClient(JiraRestClient restClient) {
        try {
            restClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
