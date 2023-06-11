package com.baeldung.repository;

import com.baeldung.model.Person;
import com.baeldung.model.Post;
import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PersonRepository {

    private final EntityManager entityManager;

    private final CriteriaBuilderFactory builderFactory;

    public PersonRepository(EntityManager entityManager, CriteriaBuilderFactory builderFactory) {
        this.entityManager = entityManager;
        this.builderFactory = builderFactory;
    }

    public Iterable<Post> findPostsByPerson() {
        CriteriaBuilder<Post> postCriteriaBuilder = builderFactory.create(entityManager, Post.class)
                .from(Person.class, "person")
                .select("person.posts");
        return postCriteriaBuilder.getResultList();
    }

    public Iterable<Person> findAll() {
        return builderFactory.create(entityManager, Person.class).getResultList();
    }

    public Iterable<Person> find() {
        CriteriaBuilder<Person> personCriteriaBuilder = builderFactory.create(entityManager, Person.class, "p")
                .where("p.age")
                    .betweenExpression("18")
                    .andExpression("40")
                .where("SIZE(p.posts)").geExpression("2")
                .orderByAsc("p.name")
                .orderByAsc("p.id");
        return personCriteriaBuilder.getResultList();
    }

}
