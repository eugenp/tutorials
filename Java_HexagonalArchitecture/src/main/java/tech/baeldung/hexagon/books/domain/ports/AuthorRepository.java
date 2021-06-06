package tech.baeldung.hexagon.books.domain.ports;

import tech.baeldung.hexagon.books.domain.model.Author;
import tech.baeldung.hexagon.books.domain.model.AuthorId;

public interface AuthorRepository {

    Author get(AuthorId authorId);
}
