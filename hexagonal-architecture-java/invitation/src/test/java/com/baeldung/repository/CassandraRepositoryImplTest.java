package com.baeldung.repository;

import com.baeldung.domain.CassandraResponse;
import com.baeldung.domain.Invitation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CassandraRepositoryImplTest {

    @InjectMocks
    CassandraRepositoryImpl cassandraRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenCassandraDBResponseWhenRepositoryCallThenReturnCreateAndReturnCassandraResponse() {

        CassandraResponse cassandraResponse = new CassandraResponse();
        cassandraResponse.setDate("01/01/2019");
        cassandraResponse.setMessage("You are cordially invited for the birthday party");

        CassandraResponse createdCassandraResponse = cassandraRepository.createInvitation(cassandraResponse);
        Assert.assertNotNull(createdCassandraResponse);
        Assert.assertEquals("01/01/2019", createdCassandraResponse.getDate());

    }

    @Test
    public void givenCassandraDBResponseWhenRepositoryCallThenGetCassandraResponse() {
        String eventId = "1";
        CassandraResponse cassandraResponse = cassandraRepository.getInvitation(eventId);
        Assert.assertNotNull(cassandraResponse);
        Assert.assertEquals("01/01/2019", cassandraResponse.getDate());

    }

}
