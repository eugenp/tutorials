package com.baeldung.multipledatamodules.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookDocumentRepository extends MongoRepository<BookDocument, String> {

}
