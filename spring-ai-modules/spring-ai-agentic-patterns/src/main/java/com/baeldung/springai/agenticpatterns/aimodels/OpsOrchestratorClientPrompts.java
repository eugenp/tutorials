package com.baeldung.springai.agenticpatterns.aimodels;

public final class OpsOrchestratorClientPrompts {

    private OpsOrchestratorClientPrompts() {
    }

    /**
     * Prompt to identify the environments which the given PR need to be tested on
     */
    public static final String REMOTE_TESTING_ORCHESTRATION_PROMPT = """
      The user should provide a PR link. From the changes of each PR, you need to decide on which environments
      these changes should be tested against. The outcome should be an array of the the PR link and then all the environments.
      User input:\s""";
}
