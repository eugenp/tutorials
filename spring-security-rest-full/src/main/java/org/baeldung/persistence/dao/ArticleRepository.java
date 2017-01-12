package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.Article;
import org.baeldung.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    public Article findByAuthorAndTitle(User user, String title);

}
