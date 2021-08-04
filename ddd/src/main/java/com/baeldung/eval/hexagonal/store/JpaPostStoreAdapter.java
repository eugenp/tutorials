package com.baeldung.eval.hexagonal.store;

import com.baeldung.eval.hexagonal.business.Post;
import com.baeldung.eval.hexagonal.business.StoreException;
import com.baeldung.eval.hexagonal.business.PostStorePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JpaPostStoreAdapter implements PostStorePort {

    @Autowired
    private JpaPostRepository jpaPostRepository;

    @Override
    public void save(Post post) throws StoreException {
        jpaPostRepository.save(getJpaPost(post));
    }

    private JpaPost getJpaPost(Post post) {
        JpaPost jpaPost = new JpaPost();
        jpaPost.setId(post.getId());
        jpaPost.setTitle(post.getTitle());
        jpaPost.setContent(post.getContent());
        return jpaPost;
    }

    @Override
    public Post read(String id) throws StoreException {
        return getPostFromJpaPost(jpaPostRepository.getById(id));
    }

    private Post getPostFromJpaPost(JpaPost jpaPost) {
        Post post = new Post();
        post.setId(jpaPost.getId());
        post.setTitle(jpaPost.getTitle());
        post.setContent(jpaPost.getContent());

        return post;
    }
}
