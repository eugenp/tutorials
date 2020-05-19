package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.port.CreatePostRequest;
import com.baeldung.architecture.hexagonal.port.CreatePostUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final CreatePostUseCase createPostUseCase;

    @Autowired
    public PostController(CreatePostUseCase createPostUseCase) {
        this.createPostUseCase = createPostUseCase;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    UUID createOrder(@RequestBody CreatePostRequest request) {
        return createPostUseCase.create(request);
    }
}
