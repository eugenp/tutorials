package com.baeldung.enumquery.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.enumquery.entity.Article;
import com.baeldung.enumquery.entity.ArticleStage;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {

    List<Article> findByStage(ArticleStage stage);

    Article findByTitleAndStage(String title, ArticleStage stage);

    List<Article> findByStageIn(List<ArticleStage> stages);

    @Query(nativeQuery = true, value = "SELECT * FROM articles WHERE stage = :#{#stage?.name()}")
    List<Article> getByStage(@Param("stage") ArticleStage stage);

    @Query(nativeQuery = true, value = "SELECT * FROM articles WHERE stage IN (:#{#stages.![name()]})")
    List<Article> getByStageIn(@Param("stages") List<ArticleStage> stages);

}