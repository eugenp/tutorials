package com.baeldung.adapter;

import com.baeldung.domain.CassandraResponse;
import com.baeldung.domain.FileWriterResponse;
import com.baeldung.domain.Invitation;
import com.baeldung.repository.FileWriterRepository;
import com.baeldung.repository.FileWriterRepositoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class FileWriterAdapterTest {

    @InjectMocks
    FileWriterAdapter fileWriterAdapter;

    @Mock
    FileWriterRepositoryImpl fileWriterRepository;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenFileResponseWhenRepositoryCallThenReturnCreateAndReturnFileResponse() {
        Invitation invitation = new Invitation();
        invitation.setInvitationDate("01/01/2019");
        invitation.setInvitationMessage("You are cordially invited for the birthday party");

        FileWriterResponse fileWriterResponse = new FileWriterResponse();
        fileWriterResponse.setDate(invitation.getInvitationDate());
        fileWriterResponse.setFileMessage(invitation.getInvitationMessage());

        when(fileWriterRepository.createInvitation(any())).thenReturn(fileWriterResponse);

        Invitation createdInvitation = fileWriterAdapter.convertToDomainToCreateInvitation(invitation);
        Assert.assertNotNull(createdInvitation);
        Assert.assertEquals("01/01/2019", createdInvitation.getInvitationDate());

    }

    @Test
    public void givenFileResponseWhenRepositoryCallThenGetFileResponse() {
        String invitationId = "1";
        Invitation invitation = new Invitation();
        invitation.setInvitationDate("01/01/2019");
        invitation.setInvitationMessage("You are cordially invited for the birthday party");

        FileWriterResponse fileWriterResponse = new FileWriterResponse();
        fileWriterResponse.setDate(invitation.getInvitationDate());
        fileWriterResponse.setFileMessage(invitation.getInvitationMessage());

        when(fileWriterRepository.getInvitation(any())).thenReturn(fileWriterResponse);

        Invitation createdInvitation = fileWriterAdapter.convertToDomainForGettingInvitation(invitationId);
        Assert.assertNotNull(createdInvitation);
        Assert.assertEquals("01/01/2019", createdInvitation.getInvitationDate());

    }
}
