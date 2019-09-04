package com.baeldung.multipledatamodules.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface BookAuditRepository extends CassandraRepository<BookAudit, String>{

}
