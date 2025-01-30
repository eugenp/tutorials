package com.baeldung.repository;

import com.baeldung.TestcontainersConfiguration;
import com.baeldung.model.Author;
import net.bytebuddy.utility.RandomString;
import org.instancio.Instancio;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class AuthorCrudLiveTest {

    @Nested
    class AuthorRepositoryLiveTest {

        @Autowired
        private AuthorRepository authorRepository;

        @Test
        void whenInsertCalledWithNewEntity_thenDocumentPersistedInDatabase() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = authorRepository.insert(author);

            Optional<Author> retrievedAuthor = authorRepository.findById(savedAuthor.getId());
            assertThat(retrievedAuthor)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(author);
        }

        @Test
        void whenInsertCalledWithExistingEntity_thenExceptionThrown() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = authorRepository.insert(author);

            assertThrows(DuplicateKeyException.class, () ->
                authorRepository.insert(savedAuthor)
            );
        }

        @Test
        void whenSaveCalled_thenNewAndExistingDocumentPersistedInDatabase() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = authorRepository.save(author);

            String updatedName = RandomString.make();
            savedAuthor.setName(updatedName);

            Author updatedAuthor = authorRepository.save(savedAuthor);
            assertThat(updatedAuthor.getName())
                .isEqualTo(updatedName);
        }

        @Test
        void whenDeleteCalled_thenDocumentDeletedFromDatabase() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = authorRepository.save(author);

            boolean authorExists = authorRepository.existsById(savedAuthor.getId());
            assertThat(authorExists)
                .isTrue();

            authorRepository.delete(savedAuthor);

            authorExists = authorRepository.existsById(savedAuthor.getId());
            assertThat(authorExists)
                .isFalse();
        }

        @Test
        void whenFindAllCalledWithPaginationAndSorting_thenCorrectSortedPageReturned() {
            int authorCount = 10;
            List<Author> authors = Instancio.ofList(Author.class)
                .size(authorCount)
                .create();
            authorRepository.saveAll(authors);

            Sort sort = Sort.by("name").ascending();
            PageRequest pageRequest = PageRequest.of(0, 5, sort);
            Page<Author> retrievedPage = authorRepository.findAll(pageRequest);
            List<Author> retrievedAuthors = retrievedPage.getContent();

            assertThat(retrievedAuthors)
                .hasSize(5);
            assertThat(retrievedAuthors.stream().map(Author::getName))
                .isSorted();

            PageRequest nextPageRequest = PageRequest.of(1, 5, sort);
            retrievedPage = authorRepository.findAll(nextPageRequest);
            retrievedAuthors = retrievedPage.getContent();

            assertThat(retrievedAuthors)
                .hasSize(5);
            assertThat(retrievedAuthors.stream().map(Author::getName))
                .isSorted();
        }

        @Test
        void whenFindByEmailCalled_thenCorrectDocumentReturned() {
            Author author = Instancio.create(Author.class);
            authorRepository.save(author);

            Optional<Author> retrievedAuthor = authorRepository.findByEmail(author.getEmail());

            assertThat(retrievedAuthor)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(author);
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        void whenFindByActiveTrueAndArticleCountGreaterThanEqualCalled_thenCorrectDocumentReturned() {
            int articleCount = 10;
            Author author = Instancio.of(Author.class)
                .set(field(Author::isActive), true)
                .set(field(Author::getArticleCount), articleCount)
                .create();
            authorRepository.save(author);

            List<Author> retrievedAuthors = authorRepository
                .findByActiveTrueAndArticleCountGreaterThanEqual(articleCount);

            assertThat(retrievedAuthors)
                .singleElement()
                .usingRecursiveComparison()
                .isEqualTo(author);
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        void findActiveAuthorsInArticleRangeCalled_thenCorrectDocumentReturned() {
            int minArticleCount = 20;
            int maxArticleCount = 50;
            Author author = Instancio.of(Author.class)
                .set(field(Author::isActive), true)
                .generate(
                    field(Author::getArticleCount),
                    gen -> gen.ints().range(minArticleCount, maxArticleCount)
                )
                .create();
            authorRepository.save(author);

            List<Author> retrievedAuthors = authorRepository
                .findActiveAuthorsInArticleRange(minArticleCount, maxArticleCount);

            assertThat(retrievedAuthors)
                .singleElement()
                .usingRecursiveComparison()
                .isEqualTo(author);
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        void whenFindActiveAuthorEmailsCalled_thenDocumentsWithOnlyEmailAttributeReturned() {
            int authorCount = 10;
            List<Author> authors = Instancio.ofList(Author.class)
                .size(authorCount)
                .set(field(Author::isActive), true)
                .create();
            authorRepository.saveAll(authors);

            List<Author> retrievedAuthors = authorRepository.findActiveAuthorEmails();

            assertThat(retrievedAuthors)
                .hasSize(authorCount)
                .allSatisfy(author -> {
                    assertThat(author)
                        .hasAllNullFieldsOrPropertiesExcept("id", "email", "articleCount");
                    assertThat(author.getArticleCount())
                        .isEqualTo(0);
                });
        }

    }

}