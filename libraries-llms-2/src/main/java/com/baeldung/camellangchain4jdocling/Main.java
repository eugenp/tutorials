package com.baeldung.camellangchain4jdocling;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class Main {

    public static void main(String[] args) throws Exception {
        OpenAiChatModel model = OpenAiChatModel.builder()
            .baseUrl("http://langchain4j.dev/demo/openai/v1")
            .apiKey("demo")
            .modelName("gpt-4o-mini")
            .build();

        org.apache.camel.main.Main main = new org.apache.camel.main.Main();
        main.configure()
            .addRoutesBuilder(new QuestionAndAnswerRoute());
        main.bind("chatModel", model);

        main.run(args);

    }
}
