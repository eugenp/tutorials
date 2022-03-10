package com.baeldung.hexagonalarchitecture.infrastructure.dao.couchbase;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

public interface UserRepositoryCouchbase extends CouchbaseRepository<UserDoc, String> {
}
