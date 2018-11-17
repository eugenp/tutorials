package com.baeldung.adapter;

import com.baeldung.domain.CassandraResponse;
import com.baeldung.domain.Invitation;
import com.baeldung.port.CassandraPort;
import com.baeldung.repository.CassandraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CassandraAdapter implements CassandraPort{

    @Autowired
    CassandraRepository cassandraRepository;

    @Override
    public Invitation convertToDomainToCreateInvitation(Invitation invitation) {
        CassandraResponse cassandraResponse = new CassandraResponse();
        cassandraResponse.setDate(invitation.getInvitationDate());
        cassandraResponse.setMessage(invitation.getInvitationMessage());
        CassandraResponse cassandraUpdatedResponse = cassandraRepository.createInvitation(cassandraResponse);

            Invitation invitationResponse = new Invitation();
            invitationResponse.setInvitationMessage(cassandraUpdatedResponse.getMessage());
            invitationResponse.setInvitationDate(cassandraUpdatedResponse.getDate());

        return invitationResponse;
    }

    @Override
    public Invitation convertToDomainForGettingInvitation(String invitationId) {
        CassandraResponse cassandraResponse = cassandraRepository.getInvitation(invitationId);

        Invitation invitationResponse = new Invitation();
        invitationResponse.setInvitationMessage(cassandraResponse.getMessage());
        invitationResponse.setInvitationDate(cassandraResponse.getDate());

        return invitationResponse;
    }


}
