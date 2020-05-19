package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.adapter.CliPostController;
import com.baeldung.architecture.hexagonal.port.CreatePostRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.UUID;

@SpringBootApplication
public class HexagonalApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(HexagonalApplication.class);

    public static void main(final String[] args) {
        SpringApplication application = new SpringApplication(HexagonalApplication.class);
        application.run(args);
    }

    @Autowired
    public CliPostController postController;

    @Autowired
    public ConfigurableApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        CreatePostRequest mobilePhone = new CreatePostRequest("CLI Title", "CLI Content");
        UUID id = postController.createPost(mobilePhone);
        LOGGER.info("Post created from CLI {}", id);
    }
}
