package com.baeldung.adapter;

import com.baeldung.domain.CassandraResponse;
import com.baeldung.domain.Invitation;
import com.baeldung.repository.CassandraRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CassandraAdapterTest {

    @InjectMocks
    CassandraAdapter cassandraAdapter;

    @Mock
    CassandraRepository cassandraRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenCassandraDBResponseWhenRepositoryCallThenReturnCreateAndReturnCassandraResponse() {
        Invitation invitation = new Invitation();
        invitation.setInvitationDate("01/01/2019");
        invitation.setInvitationMessage("You are cordially invited for the birthday party");

        CassandraResponse cassandraResponse = new CassandraResponse();
        cassandraResponse.setDate(invitation.getInvitationDate());
        cassandraResponse.setMessage(invitation.getInvitationMessage());

        when(cassandraRepository.createInvitation(any())).thenReturn(cassandraResponse);

        Invitation createdInvitation = cassandraAdapter.convertToDomainToCreateInvitation(invitation);
        Assert.assertNotNull(createdInvitation);
        Assert.assertEquals("01/01/2019", createdInvitation.getInvitationDate());

    }

    @Test
    public void givenCassandraDBResponseWhenRepositoryCallThenGetCassandraResponse() {
        String invitationId = "1";
        Invitation invitation = new Invitation();
        invitation.setInvitationDate("01/01/2019");
        invitation.setInvitationMessage("You are cordially invited for the birthday party");

        CassandraResponse cassandraResponse = new CassandraResponse();
        cassandraResponse.setDate(invitation.getInvitationDate());
        cassandraResponse.setMessage(invitation.getInvitationMessage());

        when(cassandraRepository.getInvitation(any())).thenReturn(cassandraResponse);

        Invitation createdInvitation = cassandraAdapter.convertToDomainForGettingInvitation(invitationId);
        Assert.assertNotNull(createdInvitation);
        Assert.assertEquals("01/01/2019", createdInvitation.getInvitationDate());

    }


}
