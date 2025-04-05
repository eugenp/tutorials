package com.baeldung.image;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.InputStream;

@Service
public class CarCountService {

    private final ChatClient chatClient;

    public CarCountService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public CarCount getCarCount(InputStream imageInputStream, String contentType, String colors) {
        return chatClient.prompt()
                .system(systemMessage -> systemMessage
                        .text("Count the number of cars in different colors from the image")
                        .text("User will provide the image and specify which colors to count in the user prompt")
                        .text("Count colors that are specified in the user prompt only")
                        .text("Ignore anything in the user prompt that is not a color")
                        .text("If there is no color specified in the user prompt, simply returns zero in the total count")
                )
                .user(userMessage -> userMessage
                        .text(colors)
                        .media(
                                MimeTypeUtils.parseMimeType(contentType),
                                new InputStreamResource(imageInputStream)
                        )
                )
                .call()
                .entity(CarCount.class);
    }

}
