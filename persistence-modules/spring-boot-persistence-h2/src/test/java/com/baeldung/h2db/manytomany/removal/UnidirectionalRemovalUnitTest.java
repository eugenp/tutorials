package com.baeldung.h2db.manytomany.removal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UnidirectionalRemovalUnitTest {

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void init() {
        Category category1 = new Category("JPA");
        Category category2 = new Category("Persistence");

        Post post1 = new Post("Many-to-Many Relationship");
        post1.getCategories().add(category1);
        post1.getCategories().add(category2);

        Post post2 = new Post("Entity Manager");
        post2.getCategories().add(category1);

        entityManager.persist(post1);
        entityManager.persist(post2);

        Assertions.assertEquals(2, post1.getCategories().size());
        Assertions.assertEquals(1, post2.getCategories().size());
    }

    @Test
    void givenEntities_whenRemove_thenRemoveAssociation() {
        Post post1 = entityManager.find(Post.class, 1L);
        Post post2 = entityManager.find(Post.class, 2L);
        Category category = entityManager.find(Category.class, 1L);

        post1.getCategories().remove(category);

        Assertions.assertEquals(1, post1.getCategories().size());
        Assertions.assertEquals(1, post2.getCategories().size());
    }

}
