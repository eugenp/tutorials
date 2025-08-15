package com.baeldung.springai.imagegen;

import org.springframework.ai.autoconfigure.vectorstore.mongo.MongoDBAtlasVectorStoreAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Excluding the below vector store auto-configurations to avoid start up
 * failure. Their corresponding starters are present on the classpath but are
 * only needed by other articles in the shared codebase.
 */
@SpringBootApplication(exclude = {
    RedisVectorStoreAutoConfiguration.class,
    MongoDBAtlasVectorStoreAutoConfiguration.class
})
@PropertySource("classpath:application-imagegen.properties")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}