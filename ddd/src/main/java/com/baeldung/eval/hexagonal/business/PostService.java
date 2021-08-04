package com.baeldung.eval.hexagonal.business;

import java.util.Date;
import java.util.UUID;

public class PostService implements PostClientPort {

    private static int MIN_CONTENT_LENGTH = 300;

    private PostStorePort postStorePort;

    public PostService(PostStorePort postStorePort) {
        this.postStorePort = postStorePort;
    }

    @Override
    public String savePost(Post post) throws PostServiceException {
        if(post == null) {
            throw new PostServiceException("Post object can not be null");
        }

        if(post.getTitle() == null || post.getTitle().trim().isEmpty()) {
            throw new PostServiceException("Title is empty. It is mandatory to save Post.");
        }

        if(post.getContent() == null || post.getContent().trim().isEmpty()) {
            throw new PostServiceException("Content is empty. It is mandatory to save Post.");
        }

        if(post.getContent().length() < MIN_CONTENT_LENGTH) {
            throw new PostServiceException("Content is too short. Minimum length is " + MIN_CONTENT_LENGTH);
        }

        enrichPost(post);

        try {
            postStorePort.save(post);
        } catch (StoreException e) {
            throw new PostServiceException("Unable to save post. " + e.getMessage());
        }

        return post.getId();
    }

    private void enrichPost(Post post) {
        capitalizeTitle(post);
        autoCorrectGrammar(post);

        post.setId(UUID.randomUUID().toString());
        post.setCreatedOn(new Date());
    }

    private void capitalizeTitle(Post post) {
        post.setTitle(post.getTitle().substring(0, 1).toUpperCase() + post.getTitle().substring(1));
    }

    private void autoCorrectGrammar(Post post) {
        //Logic to correct the grammar
    }

    @Override
    public Post getPost(String id) throws PostServiceException {
        if(id == null || id.trim().isEmpty()) {
            throw new PostServiceException("Id is empty. It is mandatory to get a Post.");
        }

        Post post = null;
        try {
            post = postStorePort.read(id);
        } catch (StoreException e) {
            throw new PostServiceException("Unable to read Post. " + e.getMessage());
        }

        return post;
    }
}
