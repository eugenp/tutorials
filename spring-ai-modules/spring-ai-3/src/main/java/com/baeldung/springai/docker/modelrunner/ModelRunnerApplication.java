package com.baeldung.springai.docker.modelrunner;

import org.springframework.ai.autoconfigure.chat.client.ChatClientAutoConfiguration;
import org.springframework.ai.autoconfigure.mistralai.MistralAiAutoConfiguration;
import org.springframework.ai.model.openai.autoconfigure.OpenAiAudioSpeechAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;


@SpringBootApplication(exclude = {
    ChatClientAutoConfiguration.class,
    MongoAutoConfiguration.class,
    MongoDataAutoConfiguration.class,
    org.springframework.ai.autoconfigure.vectorstore.mongo.MongoDBAtlasVectorStoreAutoConfiguration.class,
    org.springframework.ai.vectorstore.mongodb.autoconfigure.MongoDBAtlasVectorStoreAutoConfiguration.class,
    OpenAiAudioSpeechAutoConfiguration.class,
    MistralAiAutoConfiguration.class
})
class ModelRunnerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ModelRunnerApplication.class);
        app.setAdditionalProfiles("dockermodelrunner");
        app.run(args);
    }

}