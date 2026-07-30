package com.baeldung.camellangchain4jdocling;

import org.apache.camel.main.Main;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class CamelDoclingApplication {

    public static void main(String[] args) throws Exception {
        OpenAiChatModel model = OpenAiChatModel.builder()
            .baseUrl("http://langchain4j.dev/demo/openai/v1")
            .apiKey("demo")
            .modelName("gpt-4o-mini")
            .build();

        Main main = new Main();
        main.bind("chatModel", model);
        main.configure()
            .addRoutesBuilder(new QuestionAndAnswerRoute());

        main.run(args);

    }
}
