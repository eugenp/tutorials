package com.baeldung.spring.data.jpa.spel.repository;

import com.baeldung.spring.data.jpa.spel.entity.Article;
import com.baeldung.spring.data.jpa.spel.entity.ArticleWrapper;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends BaseNewsApplicationRepository<Article, Long> {


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
                   + "VALUES (?1, ?2, ?3, ?4)",
        nativeQuery = true)
    void saveWithPositionalArguments(Long id, String title, String content, String language);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
                   + "VALUES (?#{[0]}, ?#{[1]}, ?#{[2]}, ?#{[3]})",
        nativeQuery = true)
    void saveWithPositionalSpELArguments(long id, String title, String content, String language);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
                   + "VALUES (?#{[0]}, ?#{[1]}, ?#{[2] ?: 'Empty Article'}, ?#{[3]})",
        nativeQuery = true)
    void saveWithPositionalSpELArgumentsWithEmptyCheck(long id, String title, String content, String language);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
                   + "VALUES (:id, :title, :content, :language)",
        nativeQuery = true)
    void saveWithNamedArguments(@Param("id") long id, @Param("title") String title,
        @Param("content") String content, @Param("language") String language);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
                   + "VALUES (:#{#id}, :#{#title}, :#{#content}, :#{#language})",
        nativeQuery = true)
    void saveWithNamedSpELArguments(@Param("id") long id, @Param("title") String title,
        @Param("content") String content, @Param("language") String language);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
                   + "VALUES (:#{#id}, :#{#title}, :#{#content}, :#{#language.toLowerCase()})",
        nativeQuery = true)
    void saveWithNamedSpELArgumentsAndLowerCaseLanguage(@Param("id") long id, @Param("title") String title,
        @Param("content") String content, @Param("language") String language);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
                   + "VALUES (:#{#article.id}, :#{#article.title}, :#{#article.content}, :#{#article.language})",
        nativeQuery = true)
    void saveWithSingleObjectSpELArgument(@Param("article") Article article);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO articles (id, title, content, language) "
                   + "VALUES (:#{#wrapper.article.id}, :#{#wrapper.article.title}, :#{#wrapper.article.content}, :#{#wrapper.article.language})",
        nativeQuery = true)
    void saveWithSingleWrappedObjectSpELArgument(@Param("wrapper") ArticleWrapper articleWrapper);


    @Query(value = "SELECT * FROM articles WHERE language = :#{locale.language}",
        nativeQuery = true)
    List<Article> findAllArticlesUsingLocaleWithNativeQuery();
}
