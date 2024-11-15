package com.baeldung.imagegen;

import java.util.Optional;

import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageGenerator {

    private final ImageModel imageModel;

    public ImageGenerator(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public String generate(String prompt) {
        ImagePrompt imagePrompt = new ImagePrompt(prompt);
        ImageResponse imageResponse = imageModel.call(imagePrompt);
        return resolveImageContent(imageResponse);
    }

    public String generate(ImageGenerationRequest request) {
        ImageOptions imageOptions = OpenAiImageOptions
            .builder()
            .withUser(request.username())
            .withHeight(request.height())
            .withWidth(request.width())
            .build();
        ImagePrompt imagePrompt = new ImagePrompt(request.prompt(), imageOptions);

        ImageResponse imageResponse = imageModel.call(imagePrompt);
        return resolveImageContent(imageResponse);
    }

    private String resolveImageContent(ImageResponse imageResponse) {
        Image image = imageResponse.getResult().getOutput();
        return Optional
            .ofNullable(image.getUrl())
            .orElseGet(image::getB64Json);
    }

}