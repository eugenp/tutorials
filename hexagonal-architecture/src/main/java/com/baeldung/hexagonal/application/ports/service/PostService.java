package com.baeldung.hexagonal.application.ports.service;

import com.baeldung.hexagonal.domain.bo.PostBo;

public interface PostService {

    PostBo addNewPost(PostBo post);

    void submitForReview(Long id);

    void review(Long id);

    void publish(Long id);

    PostBo findPostById(Long id);
}
