package com.baeldung.eval.hexagonal.client;

import com.baeldung.eval.hexagonal.business.Post;
import com.baeldung.eval.hexagonal.business.PostClientPort;
import com.baeldung.eval.hexagonal.business.PostServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostClientAdapter {

    private PostClientPort postClientPort;

    @Autowired
    public PostClientAdapter(PostClientPort postClientPort) {
        this.postClientPort = postClientPort;
    }

    public Post getPost(String id) throws PostServiceException {
        return postClientPort.getPost(id);
    }

    public String savePost(Post post) throws PostServiceException {
        return postClientPort.savePost(post);
    }
}
