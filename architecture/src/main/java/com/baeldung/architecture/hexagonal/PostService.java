package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.port.CreatePostRequest;
import com.baeldung.architecture.hexagonal.port.CreatePostUseCase;
import com.baeldung.architecture.hexagonal.port.SavePostPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class PostService implements CreatePostUseCase {

    private final SavePostPort savePostPort;

    @Autowired
    public PostService(SavePostPort savePostPort) {
        this.savePostPort = savePostPort;
    }

    @Override
    public UUID create(CreatePostRequest request) {
        Post order = new Post(request.getTitle(), request.getContent());
        savePostPort.save(order);
        return order.getId();
    }
}
