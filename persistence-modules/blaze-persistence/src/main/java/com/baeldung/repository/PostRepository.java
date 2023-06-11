package com.baeldung.repository;

import com.baeldung.model.Post;
import com.baeldung.view.PostWithAuthorView;
import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class PostRepository {

    private final EntityManager entityManager;

    private final CriteriaBuilderFactory builderFactory;

    private final EntityViewManager viewManager;

    public PostRepository(EntityManager entityManager, CriteriaBuilderFactory builderFactory,
                          EntityViewManager viewManager) {
        this.entityManager = entityManager;
        this.builderFactory = builderFactory;
        this.viewManager = viewManager;
    }

    public Iterable<Post> findAll() {
        return builderFactory.create(entityManager, Post.class).getResultList();
    }

    public Iterable<PostWithAuthorView> findBy(final String title, final String authorName) {
        CriteriaBuilder<Post> postCriteriaBuilder = builderFactory.create(entityManager, Post.class, "p")
                .whereOr()
                    .where("p.title").like().value(title + "%").noEscape()
                    .where("p.author.name").eq(authorName)
                .endOr();

        CriteriaBuilder<PostWithAuthorView> postWithAuthorViewCriteriaBuilder =
                viewManager.applySetting(EntityViewSetting
                        .create(PostWithAuthorView.class), postCriteriaBuilder);

        return postWithAuthorViewCriteriaBuilder.getResultList();
    }
}
