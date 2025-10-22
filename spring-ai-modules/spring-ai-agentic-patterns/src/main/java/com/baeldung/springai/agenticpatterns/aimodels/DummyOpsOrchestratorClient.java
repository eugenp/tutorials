package com.baeldung.springai.agenticpatterns.aimodels;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DummyOpsOrchestratorClient implements OpsOrchestratorClient {

    @Override
    @NonNull
    public ChatClientRequestSpec prompt() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @NonNull
    public ChatClientRequestSpec prompt(@NonNull String request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @NonNull
    public ChatClientRequestSpec prompt(@NonNull Prompt prompt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @NonNull
    public Builder mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
