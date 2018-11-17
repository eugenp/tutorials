package com.baeldung.service;

import com.baeldung.domain.Invitation;
import com.baeldung.port.CassandraPort;
import com.baeldung.port.FileWriterPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    FileWriterPort fileWriterPort;

    @Autowired
    CassandraPort cassandraPort;

    @Value("${invitation.dataSource.name}")
    private String dataSourceName;

    @Override
    public Invitation createInvitation(Invitation invitation) {

        Invitation createdInvitation = null;

        // If cassandra flag is enabled, use cassandra as db otherwise use file as db.
        if (dataSourceName.equals("cassandra")) {
            createdInvitation = cassandraPort.convertToDomainToCreateInvitation(invitation);
        } else {
            createdInvitation = fileWriterPort.convertToDomainToCreateInvitation(invitation);
        }

        return createdInvitation;
    }

    @Override
    public Invitation getInvitation(String invitationId) {
        Invitation invitation = null;

        // If cassandra flag is enabled, use cassandra as db otherwise use file as db.
        if (dataSourceName.equals("cassandra")) {
            invitation = cassandraPort.convertToDomainForGettingInvitation(invitationId);
        } else {
            invitation = fileWriterPort.convertToDomainForGettingInvitation(invitationId);
        }

        return invitation;
    }

}
