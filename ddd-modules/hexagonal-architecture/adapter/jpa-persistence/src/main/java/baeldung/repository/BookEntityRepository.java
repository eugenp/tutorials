package baeldung.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BookEntityRepository extends CrudRepository<BookEntity, UUID> {

    List<BookEntity> findByTitleOrAuthor(String title, String author);

}
