package com.baeldung.specifications.join;

import static com.baeldung.specifications.join.AuthorSpecifications.hasBookWithTitle;
import static com.baeldung.specifications.join.AuthorSpecifications.hasFirstNameLike;
import static com.baeldung.specifications.join.AuthorSpecifications.hasLastName;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

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

        BookAuthorEntity bookAuthorEntity1 = new BookAuthorEntity();
        bookAuthorEntity1.setTitle("Clean Code");
        BookAuthorEntity bookAuthorEntity2 = new BookAuthorEntity();
        bookAuthorEntity2.setTitle("Clean Architecture");

        uncleBob.setBookAuthorEntities(Arrays.asList(bookAuthorEntity1, bookAuthorEntity2));
        repository.save(uncleBob);
    }
}
