package com.baeldung.jpa.entitygraph.repo;

import com.baeldung.jpa.entitygraph.model.Post;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Map;

public class PostRepository {
    private EntityManagerFactory emf = null;


    public PostRepository() {
        Map properties = new HashMap();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        emf = Persistence.createEntityManagerFactory("entity-graph-pu", properties);
    }

    public Post find(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        Post post = entityManager.find(Post.class, id);

        entityManager.close();
        return post;
    }

    public Post findWithEntityGraph(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        EntityGraph entityGraph = entityManager.getEntityGraph("post-entity-graph");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        Post post = entityManager.find(Post.class, id, properties);

        entityManager.close();
        return post;
    }

    public Post findWithEntityGraph2(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        EntityGraph<Post> entityGraph = entityManager.createEntityGraph(Post.class);
        entityGraph.addAttributeNodes("subject");
        entityGraph.addAttributeNodes("user");
        entityGraph.addSubgraph("comments")
                .addAttributeNodes("user");

        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        Post post = entityManager.find(Post.class, id, properties);

        entityManager.close();
        return post;
    }

    public Post findUsingJpql(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        EntityGraph entityGraph = entityManager.getEntityGraph("post-entity-graph-with-comment-users");
        Post post = entityManager.createQuery("Select p from Post p where p.id=:id", Post.class)
                .setParameter("id", id)
                .setHint("jakarta.persistence.fetchgraph", entityGraph)
                .getSingleResult();

        entityManager.close();
        return post;
    }

    public Post findUsingCriteria(Long id) {
        EntityManager entityManager = emf.createEntityManager();

        EntityGraph entityGraph = entityManager.getEntityGraph("post-entity-graph-with-comment-users");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.where(criteriaBuilder.equal(root.<Long>get("id"), id));
        TypedQuery<Post> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setHint("jakarta.persistence.loadgraph", entityGraph);
        Post post = typedQuery.getSingleResult();

        entityManager.close();
        return post;
    }

    public void clean() {
        emf.close();
    }
}
