package com.baeldung.spring.data.jpa.spel.repository;

import com.baeldung.spring.data.jpa.spel.entity.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseNewsApplicationRepository<T, I> extends JpaRepository<T, I> {

    @Query(value = "select e from #{#entityName} e")
    List<Article> findAllEntitiesUsingEntityPlaceholder();

    @Query(value = "SELECT * FROM #{#entityName}", nativeQuery = true)
    List<Article> findAllEntitiesUsingEntityPlaceholderWithNativeQuery();
}
