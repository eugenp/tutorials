package com.baeldung.spring.pkl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitHubService {

    private final Logger logger = LoggerFactory.getLogger(GitHubService.class);
    private ToolIntegration toolIntegration;

    public GitHubService(ToolIntegration toolIntegration) {
        this.toolIntegration = toolIntegration;
    }

    public ToolIntegration getToolIntegration() {
        return toolIntegration;
    }
}
