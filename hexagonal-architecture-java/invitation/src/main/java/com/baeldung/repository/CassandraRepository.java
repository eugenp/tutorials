package com.baeldung.repository;

import com.baeldung.domain.CassandraResponse;

public interface CassandraRepository {

    CassandraResponse createInvitation(CassandraResponse cassandraResponse);
    CassandraResponse getInvitation(String eventId);
}
