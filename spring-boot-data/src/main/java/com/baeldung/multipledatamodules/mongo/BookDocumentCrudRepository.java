package com.baeldung.multipledatamodules.mongo;

import org.springframework.data.repository.CrudRepository;

public interface BookDocumentCrudRepository extends CrudRepository<BookDocument, String> {

}
