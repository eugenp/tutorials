package com.baeldung.hexagonal.core.ports.service;

import com.baeldung.hexagonal.core.domain.bo.PostBo;

import java.util.List;

public interface PostService {

    PostBo addNewPost(PostBo post);

    void submitForReview(Long id);

    void review(Long id);

    void publish(Long id);

    PostBo findPostById(Long id);

    void deletePostById(Long id);

    List<PostBo> findAllPosts();
}
