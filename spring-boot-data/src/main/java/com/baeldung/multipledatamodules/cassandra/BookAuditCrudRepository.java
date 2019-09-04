package com.baeldung.multipledatamodules.cassandra;

import org.springframework.data.repository.CrudRepository;

public interface BookAuditCrudRepository extends CrudRepository<BookAudit, String>{

}
