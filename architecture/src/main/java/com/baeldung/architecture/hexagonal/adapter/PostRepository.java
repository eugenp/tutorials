package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.Post;
import com.baeldung.architecture.hexagonal.port.SavePostPort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PostRepository implements SavePostPort {

    private final Map<UUID, Post> posts = new ConcurrentHashMap<>();

    @Override
    public void save(Post post) {
        posts.put(post.getId(), post);
    }
}
