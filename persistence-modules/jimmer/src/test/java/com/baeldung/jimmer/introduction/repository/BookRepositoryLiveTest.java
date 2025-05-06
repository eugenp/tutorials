package com.baeldung.jimmer.introduction.repository;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.jimmer.introduction.Application;
import com.baeldung.jimmer.introduction.dto.BookView;

@SpringBootTest(classes = Application.class)
class BookRepositoryLiveTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("init.sql"));
    }

    @Test
    @Transactional
    void whenInsertingData_thenDataShouldBeInsertedAndReadSubsequently() {

        // given.
        bookRepository.saveAdHocBookDraft("Baeldung");

        // when.
        List<BookView> found = bookRepository.findAllByTitleLike("Bael");

        // then.
        Assertions.assertThat(found)
          .hasSize(1)
          .first()
          .extracting(BookView::getTitle)
          .isEqualTo("Baeldung");
    }
}
