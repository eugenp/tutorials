package com.baeldung.repository;

import com.baeldung.domain.CassandraResponse;
import org.springframework.stereotype.Repository;

@Repository
public class CassandraRepositoryImpl implements CassandraRepository {

    @Override
    public CassandraResponse createInvitation(CassandraResponse cassandraResponse) {

        // TODO : Write invitation in cassandra DB
        return cassandraResponse;
    }

    @Override
    public CassandraResponse getInvitation(String eventId) {

        //TODO : Read invitation from cassandra db. Creating the object and returning temporarily

        CassandraResponse cassandraResponse = new CassandraResponse();
        cassandraResponse.setDate("01/01/2019");
        cassandraResponse.setMessage("You are cordially invited for the birthday party");
        return cassandraResponse;
    }
}
