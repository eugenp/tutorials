package com.baeldung.jpa.service;

import com.baeldung.jpa.dao.PostRepository;
import com.baeldung.jpa.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class PostService {

    private PostRepository postRepository;
    private TransactionTemplate transactionTemplate;
    private EntityManager entityManager;

    @Autowired
    public PostService(PostRepository postRepository, TransactionTemplate transactionTemplate,
                       EntityManager entityManager) {
        this.postRepository = postRepository;
        this.transactionTemplate = transactionTemplate;
        this.entityManager = entityManager;
    }

    @Transactional
    public void updatePost(int id, String title, String body) {
        postRepository.updateTitleAndBodyById(id, title, body);
    }

    public void updateWithTransactionTemplate(int id, String title, String body) {

        transactionTemplate.execute(transactionStatus -> {
            entityManager.createQuery("UPDATE Post p SET p.title = ?2, p.body = ?3 WHERE p.id = ?1")
                    .setParameter(1, id)
                    .setParameter(2, title)
                    .setParameter(3, body)
                    .executeUpdate();
            return null;
        });

        //proceed to do other stuffs like send emails
    }



}
