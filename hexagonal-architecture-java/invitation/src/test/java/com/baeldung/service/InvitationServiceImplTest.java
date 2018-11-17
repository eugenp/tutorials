package com.baeldung.service;

import com.baeldung.domain.Invitation;
import com.baeldung.port.CassandraPort;
import com.baeldung.port.FileWriterPort;
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
public class InvitationServiceImplTest {

    @InjectMocks
    InvitationServiceImpl invitationService;

    @Mock
    CassandraPort cassandraPort;

    @Mock
    FileWriterPort fileWriterPort;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(invitationService, "dataSourceName", "cassandra");
    }

    @Test
    public void givenThereIsBirthdayWhenInvitationIsNotThenWriteAnInvitationInCassandraDB() {
        Invitation invitation = new Invitation();
        invitation.setInvitationDate("01/01/2019");
        invitation.setInvitationMessage("You are cordially invited for the birthday party");
        ReflectionTestUtils.setField(invitationService, "dataSourceName", "cassandra");
        when(cassandraPort.convertToDomainToCreateInvitation(any())).thenReturn(invitation);

        Invitation createdInvitation = invitationService.createInvitation(invitation);
        Assert.assertNotNull(createdInvitation);
        Assert.assertEquals("01/01/2019", createdInvitation.getInvitationDate());

    }

    @Test
    public void givenThereIsBirthdayWhenInvitationIsNotThenWriteAnInvitationInFile() {
        Invitation invitation = new Invitation();
        invitation.setInvitationDate("01/01/2019");
        invitation.setInvitationMessage("You are cordially invited for the birthday party");
        ReflectionTestUtils.setField(invitationService, "dataSourceName", "file");
        when(fileWriterPort.convertToDomainToCreateInvitation(any())).thenReturn(invitation);

        Invitation createdInvitation = invitationService.createInvitation(invitation);
        Assert.assertNotNull(createdInvitation);
        Assert.assertEquals("01/01/2019", createdInvitation.getInvitationDate());

    }

    @Test
    public void givenThereIsBirthdayWhenInvitationIsNotThenReadAnInvitationInCassandraDB() {
        String invitationId = "1";
        Invitation invitation = new Invitation();
        invitation.setInvitationDate("01/01/2019");
        invitation.setInvitationMessage("You are cordially invited for the birthday party");
        ReflectionTestUtils.setField(invitationService, "dataSourceName", "cassandra");
        when(cassandraPort.convertToDomainForGettingInvitation(any())).thenReturn(invitation);

        Invitation getInvitation = invitationService.getInvitation(invitationId);
        Assert.assertNotNull(getInvitation);
        Assert.assertEquals("01/01/2019", getInvitation.getInvitationDate());

    }

    @Test
    public void givenThereIsBirthdayWhenInvitationIsNotThenReadAnInvitationInFile() {
        String invitationId = "1";
        Invitation invitation = new Invitation();
        invitation.setInvitationDate("01/01/2019");
        invitation.setInvitationMessage("You are cordially invited for the birthday party");

        ReflectionTestUtils.setField(invitationService, "dataSourceName", "file");
        when(fileWriterPort.convertToDomainForGettingInvitation(any())).thenReturn(invitation);

        Invitation getInvitation = invitationService.getInvitation(invitationId);
        Assert.assertNotNull(getInvitation);
        Assert.assertEquals("01/01/2019", getInvitation.getInvitationDate());

    }


}
