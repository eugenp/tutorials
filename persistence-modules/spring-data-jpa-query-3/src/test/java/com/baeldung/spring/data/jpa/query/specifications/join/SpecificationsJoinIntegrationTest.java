package com.baeldung.spring.data.jpa.query.specifications.join;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.baeldung.spring.data.jpa.query.specifications.join.AuthorSpecifications.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SpecificationsJoinIntegrationTest {

    @Autowired
    private AuthorsRepository repository;

    @Before
    public void beforeEach() {
        saveTestData();
    }

    @Test
    public void whenSearchingByLastName_thenOneAuthorIsReturned() {

        List<Author> authors = repository.findAll(hasLastName("Martin"));

        assertThat(authors).hasSize(1);
    }

    @Test
    public void whenSearchingByLastNameAndFirstNameLike_thenOneAuthorIsReturned() {

        Specification<Author> specification = hasLastName("Martin").and(hasFirstNameLike("Robert"));

        List<Author> authors = repository.findAll(specification);

        assertThat(authors).hasSize(1);
    }

    @Test
    public void whenSearchingByBookTitle_thenOneAuthorIsReturned() {

        Specification<Author> specification = hasBookWithTitle("Clean Code");

        List<Author> authors = repository.findAll(specification);

        assertThat(authors).hasSize(1);
    }

    @Test
    public void whenSearchingByBookTitleAndAuthorName_thenOneAuthorIsReturned() {

        Specification<Author> specification = hasLastName("Martin").and(hasBookWithTitle("Clean Code"));

        List<Author> authors = repository.findAll(specification);

        assertThat(authors).hasSize(1);
    }

    private void saveTestData() {
        Author uncleBob = new Author();
        uncleBob.setFirstName("Robert");
        uncleBob.setLastName("Martin");

        Book book1 = new Book();
        book1.setTitle("Clean Code");
        Book book2 = new Book();
        book2.setTitle("Clean Architecture");

        uncleBob.setBooks(Arrays.asList(book1, book2));
        repository.save(uncleBob);
    }
}
