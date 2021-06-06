package tech.baeldung.hexagon.books.adapters.authorservice;

import org.springframework.stereotype.Component;
import tech.baeldung.hexagon.books.domain.model.Author;
import tech.baeldung.hexagon.books.domain.model.AuthorId;
import tech.baeldung.hexagon.books.domain.ports.AuthorRepository;

import static tech.baeldung.hexagon.books.adapters.authorservice.AuthorExternalModel.authorExternalModel;

@Component
class ExternalServiceClientAuthorRepository implements AuthorRepository {


    @Override
    public Author get(final AuthorId authorId) {
        /**
         * external author service integration implementation comes here
         */
        final AuthorExternalModel author = authorExternalModel()
                .withId(928467)
                .withFirstName("William")
                .withLastName("Shakespeare")
                .build();
        return author.toDomain();
    }
}
