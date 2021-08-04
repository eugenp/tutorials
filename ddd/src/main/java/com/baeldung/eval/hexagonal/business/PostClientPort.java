package com.baeldung.eval.hexagonal.business;

public interface PostClientPort {
    String savePost(Post post) throws PostServiceException;

    Post getPost(String id) throws PostServiceException;
}
