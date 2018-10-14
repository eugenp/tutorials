package com.baeldung.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AssistantConfig {

    @Bean
    public AssistantCore assistantCore() {
        AnswerPort answerPort = new TextAnswerAdapter();
        AssistantCore core = new AssistantCore(answerPort);
        return core;
    }

    @Bean
    public TextInputCommandAdapter commandListenerPort(@Autowired AssistantCore core) {
        return new TextInputCommandAdapter(core);
    }

}
