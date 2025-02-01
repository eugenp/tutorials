package com.baeldung.spring.pkl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfiguration {
    @Bean
    public GitHubService getGitHubService(ToolIntegrationProperties toolIntegration) {
        return new GitHubService(toolIntegration.getGitConnection());
    }

    @Bean
    public JiraService getJiraService(ToolIntegrationProperties toolIntegration) {
        return new JiraService(toolIntegration.getJiraConnection());
    }
}
