package com.baeldung.adapter;

import com.baeldung.domain.FileWriterResponse;
import com.baeldung.domain.Invitation;
import com.baeldung.port.FileWriterPort;
import com.baeldung.repository.FileWriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileWriterAdapter implements FileWriterPort{

    @Autowired
    FileWriterRepository fileWriterRepository;

    @Override
    public Invitation convertToDomainToCreateInvitation(Invitation invitation) {
        FileWriterResponse fileWriterResponse = new FileWriterResponse();
        fileWriterResponse.setDate(invitation.getInvitationDate());
        fileWriterResponse.setFileMessage(invitation.getInvitationMessage());
        FileWriterResponse fileWriterUpdatedResponse = fileWriterRepository.createInvitation(fileWriterResponse);

        Invitation invitationResponse = new Invitation();
        invitationResponse.setInvitationMessage(fileWriterUpdatedResponse.getFileMessage());
        invitationResponse.setInvitationDate(fileWriterUpdatedResponse.getDate());

        return invitationResponse;
    }

    @Override
    public Invitation convertToDomainForGettingInvitation(String invitationId) {
        FileWriterResponse cassandraResponse = fileWriterRepository.getInvitation(invitationId);

        Invitation invitationResponse = new Invitation();
        invitationResponse.setInvitationMessage(cassandraResponse.getFileMessage());
        invitationResponse.setInvitationDate(cassandraResponse.getDate());

        return invitationResponse;
    }
}
