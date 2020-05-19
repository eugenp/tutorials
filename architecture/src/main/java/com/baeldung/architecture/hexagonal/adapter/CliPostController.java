package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.PostService;
import com.baeldung.architecture.hexagonal.port.CreatePostRequest;
import com.baeldung.architecture.hexagonal.port.CreatePostUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CliPostController {

    private final CreatePostUseCase createPostUseCase;

    @Autowired
    public CliPostController(CreatePostUseCase createPostUseCase) {
        this.createPostUseCase = createPostUseCase;
    }

    public UUID createPost(CreatePostRequest request) {
        return createPostUseCase.create(request);
    }
}
