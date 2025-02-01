package com.baeldung.repository;

import com.baeldung.Application;
import com.baeldung.TestcontainersConfiguration;
import com.baeldung.model.Author;
import com.mongodb.client.result.UpdateResult;
import net.bytebuddy.utility.RandomString;
import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = Application.class)
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
            authorRepository.save(savedAuthor);

            Optional<Author> updatedAuthor = authorRepository.findById(savedAuthor.getId());
            assertThat(updatedAuthor)
                .isPresent()
                .get()
                .extracting(Author::getName)
                .isEqualTo(updatedName);
        }

        @Test
        void whenDeleteCalled_thenDocumentDeletedFromDatabase() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = authorRepository.save(author);

            Boolean authorExists = authorRepository.existsById(savedAuthor.getId());
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
            List<Author> retrievedAuthors = authorRepository.findAll(pageRequest).getContent();

            assertThat(retrievedAuthors)
                .hasSize(5)
                .extracting(Author::getName)
                .isSorted();

            PageRequest nextPageRequest = PageRequest.of(1, 5, sort);
            retrievedAuthors = authorRepository.findAll(nextPageRequest).getContent();

            assertThat(retrievedAuthors)
                .hasSize(5)
                .extracting(Author::getName)
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
                .generate(field(Author::getArticleCount), gen -> gen.ints().min(articleCount))
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
                .allSatisfy(author -> assertThat(author)
                    .hasAllNullFieldsOrPropertiesExcept("id", "email"));
        }

    }

    @Nested
    class AuthorMongoTemplateLiveTest {

        @Autowired
        private MongoTemplate mongoTemplate;

        @Test
        void whenInsertCalledWithNewEntity_thenDocumentPersistedInDatabase() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = mongoTemplate.insert(author);

            Author retrievedAuthor = mongoTemplate.findById(savedAuthor.getId(), Author.class);
            assertThat(retrievedAuthor)
                .usingRecursiveComparison()
                .isEqualTo(author);
        }

        @Test
        void whenInsertCalledWithExistingEntity_thenExceptionThrown() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = mongoTemplate.insert(author);

            assertThrows(DuplicateKeyException.class, () ->
                mongoTemplate.insert(savedAuthor)
            );
        }

        @Test
        void whenSaveCalled_thenNewAndExistingDocumentPersistedInDatabase() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = mongoTemplate.save(author);

            String updatedName = RandomString.make();
            savedAuthor.setName(updatedName);
            mongoTemplate.save(savedAuthor);

            Author updatedAuthor = mongoTemplate.findById(savedAuthor.getId(), Author.class);
            assertThat(updatedAuthor)
                .extracting(Author::getName)
                .isEqualTo(updatedName);
        }

        @Test
        void whenRemoveCalled_thenDocumentDeletedFromDatabase() {
            Author author = Instancio.create(Author.class);
            Author savedAuthor = mongoTemplate.save(author);

            Author retrievedAuthor = mongoTemplate.findById(savedAuthor.getId(), Author.class);
            assertThat(retrievedAuthor)
                .isNotNull();

            mongoTemplate.remove(retrievedAuthor);

            retrievedAuthor = mongoTemplate.findById(savedAuthor.getId(), Author.class);
            assertThat(retrievedAuthor)
                .isNull();
        }

        @Test
        void whenSearchingWithAndCriteria_thenCorrectDocumentsReturned() {
            Author author = Instancio.of(Author.class)
                .set(field(Author::isActive), false)
                .generate(field(Author::getEmail), gen -> gen.text().pattern("#a#a#a@baeldung.com"))
                .create();
            mongoTemplate.save(author);

            Criteria nonActive = Criteria.where("active").is(false);
            Criteria baeldungEmail = Criteria.where("email").regex("@baeldung\\.com$");
            Query query = new Query();
            query.addCriteria(nonActive);
            query.addCriteria(baeldungEmail);

            List<Author> retrievedAuthors = mongoTemplate.find(query, Author.class);
            assertThat(retrievedAuthors)
                .singleElement()
                .usingRecursiveComparison()
                .isEqualTo(author);
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        void whenSearchingWithOrCriteria_thenCorrectDocumentsReturned() {
            int articleCount = 100;
            Author highArticleCountAuthor = Instancio.of(Author.class)
                .set(field(Author::getArticleCount), articleCount)
                .generate(field(Author::getArticleCount), gen -> gen.ints().min(articleCount))
                .create();

            Author namelessAuthor = Instancio.of(Author.class)
                .ignore(field(Author::getName))
                .generate(field(Author::getArticleCount), gen -> gen.ints().max(articleCount))
                .create();

            mongoTemplate.insertAll(List.of(highArticleCountAuthor, namelessAuthor));

            Criteria highArticleCount = Criteria.where("article_count").gte(articleCount);
            Criteria missingName = Criteria.where("full_name").exists(false);
            Criteria criteria = new Criteria();
            criteria.orOperator(highArticleCount, missingName);
            Query query = new Query();
            query.addCriteria(criteria);

            List<Author> retrievedAuthors = mongoTemplate.find(query, Author.class);
            assertThat(retrievedAuthors)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .contains(highArticleCountAuthor, namelessAuthor);
        }

        @Test
        void whenUpdateFirstCalled_thenOnlySingleDocumentUpdated() {
            String name = RandomString.make();
            Model<Author> sameNameAuthorModel = Instancio.of(Author.class)
                .set(field(Author::isActive), true)
                .set(field(Author::getName), name)
                .toModel();

            int authorCount = 10;
            for (int i = 0; i < authorCount; i++) {
                Author author = Instancio.create(sameNameAuthorModel);
                mongoTemplate.save(author);
            }

            Query query = new Query(Criteria.where("name").is(name));
            Update update = new Update();
            update.set("active", false);

            UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Author.class);
            assertThat(updateResult.getModifiedCount())
                .isEqualTo(1);
        }

        @Test
        @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        void whenUpdateMultiCalled_thenAllDocumentsUpdated() {
            int lowArticleCount = 10;
            int authorCount = 20;
            for (int i = 0; i < 20; i++) {
                Author lowArticleCountAuthor = Instancio.of(Author.class)
                    .set(field(Author::isActive), true)
                    .generate(field(Author::getArticleCount), gen -> gen.ints().max(lowArticleCount))
                    .create();
                mongoTemplate.save(lowArticleCountAuthor);
            }

            Query query = new Query(Criteria.where("article_count").lte(lowArticleCount));
            Update update = new Update();
            update.set("active", false);
            UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Author.class);

            assertThat(updateResult.getModifiedCount())
                .isEqualTo(authorCount);
        }

        @Test
        void whenUpsertCalledForNonExistingDocument_thenNewDocumentPersistedInDatabase() {
            UUID authorId = UUID.randomUUID();
            String email = RandomString.make() + "@baeldung.com";
            String name = RandomString.make();

            Query query = new Query(Criteria.where("email").is(email));
            Update update = new Update()
                .set("name", name)
                .setOnInsert("id", authorId)
                .setOnInsert("active", true);

            mongoTemplate.upsert(query, update, Author.class);

            Author retrievedAuthor = mongoTemplate.findOne(query, Author.class);
            assertThat(retrievedAuthor)
                .isNotNull()
                .satisfies(author -> {
                    assertThat(author.getEmail()).isEqualTo(email);
                    assertThat(author.getName()).isEqualTo(name);
                    assertThat(author.isActive()).isTrue();
                });
        }

        @Test
        void whenUpsertCalledForExistingDocument_thenDocumentUpdated() {
            Author author = Instancio.create(Author.class);
            mongoTemplate.save(author);

            String updatedName = RandomString.make();
            Query query = new Query(Criteria.where("email").is(author.getEmail()));
            Update update = new Update();
            update.set("name", updatedName);

            mongoTemplate.upsert(query, update, Author.class);

            Author retrievedAuthor = mongoTemplate.findOne(query, Author.class);
            assertThat(retrievedAuthor.getName())
                .isEqualTo(updatedName);
        }

    }

}