package com.baeldung.spring.data.jpa.query.nonuniqueresultexception;

import com.baeldung.spring.data.jpa.query.datetime.ArticleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.NonUniqueResultException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.sql.init.data-locations=classpath:import_entities.sql", showSql = false)
public class NonUniqueResultExceptionIntegrationTest {

    @Autowired
    private ArticleRepository repository;

    @Test
    public void givenImportedArticles_whenFindByPublicationTimeBetween_thenIncorrectResultSizeDataAccessExceptionThrown() {
        assertThatThrownBy(() -> repository.findByPublicationTimeBetween(new SimpleDateFormat("HH:mm").parse("15:15"), new SimpleDateFormat("HH:mm").parse("16:30")))
                .isInstanceOf(IncorrectResultSizeDataAccessException.class)
                .hasCauseInstanceOf(NonUniqueResultException.class);
    }

    @Test
    public void givenImportedArticles_whenFindAllByPublicationTimeBetween_thenSuccess() throws ParseException {
        repository.findAllByPublicationTimeBetween(new SimpleDateFormat("HH:mm").parse("15:15"), new SimpleDateFormat("HH:mm").parse("16:30"));
    }
}
