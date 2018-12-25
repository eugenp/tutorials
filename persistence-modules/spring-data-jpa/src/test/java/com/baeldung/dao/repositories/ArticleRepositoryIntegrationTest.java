package com.baeldung.dao.repositories;

import com.baeldung.config.PersistenceConfiguration;
import com.baeldung.config.PersistenceProductConfiguration;
import com.baeldung.config.PersistenceUserConfiguration;
import com.baeldung.domain.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest(excludeAutoConfiguration = {PersistenceConfiguration.class, PersistenceUserConfiguration.class, PersistenceProductConfiguration.class})
public class ArticleRepositoryIntegrationTest {

    @Autowired
    private ArticleRepository repository;

    @Test
    public void givenImportedArticlesWhenFindAllByPublicationDateThenArticles1And2Returned()
      throws Exception {
        List<Article> result = repository.findAllByPublicationDate(
          new SimpleDateFormat("yyyy-MM-dd").parse("2018-01-01")
        );

        assertEquals(2, result.size());
        assertTrue(result.stream()
          .map(Article::getId)
          .allMatch(id -> Arrays.asList(1, 2).contains(id))
        );
    }

    @Test
    public void givenImportedArticlesWhenFindAllByPublicationTimeBetweenThenArticles2And3Returned()
      throws Exception {
        List<Article> result = repository.findAllByPublicationTimeBetween(
          new SimpleDateFormat("HH:mm").parse("15:15"),
          new SimpleDateFormat("HH:mm").parse("16:30")
        );

        assertEquals(2, result.size());
        assertTrue(result.stream()
          .map(Article::getId)
          .allMatch(id -> Arrays.asList(2, 3).contains(id))
        );
    }

    @Test
    public void givenImportedArticlesWhenFindAllWithCreationDateTimeBeforeThenArticles2And3Returned() throws Exception {
        List<Article> result = repository.findAllWithCreationDateTimeBefore(
          new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2017-12-15 10:00")
        );

        assertEquals(2, result.size());
        assertTrue(result.stream()
          .map(Article::getId)
          .allMatch(id -> Arrays.asList(2, 3).contains(id))
        );
    }

}
