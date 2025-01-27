package com.baeldung.spring.pkl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfiguration {

    @Bean
    public GitHubService getGitHubService(ToolIntegration toolIntegration) {
        return new GitHubService(toolIntegration);
    }

}
