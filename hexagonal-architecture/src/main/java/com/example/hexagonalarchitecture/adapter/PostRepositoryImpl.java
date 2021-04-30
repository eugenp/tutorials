package com.example.hexagonalarchitecture.adapter;

import com.example.hexagonalarchitecture.domain.Post;
import com.example.hexagonalarchitecture.port.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final List<Post> storage;

    public PostRepositoryImpl() {
        storage = new ArrayList<>();
    }

    @Override
    public List<Post> getAll() {
        return storage;
    }

    @Override
    public Post getById(Integer id) {
        return storage.stream().filter(it -> it.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Post create(Post post) {
        storage.add(post);
        return post;
    }

    @Override
    public Post update(Post post) {
        int index = IntStream.range(0, storage.size())
                .filter(i -> post.getId().equals(storage.get(i).getId()))
                .findFirst().orElse(-1);
        storage.set(index, post);
        return post;
    }

    @Override
    public void deleteById(Integer id) {
        int index = IntStream.range(0, storage.size())
                .filter(i -> id.equals(storage.get(i).getId()))
                .findFirst().orElse(-1);
        storage.remove(index);
    }
}
