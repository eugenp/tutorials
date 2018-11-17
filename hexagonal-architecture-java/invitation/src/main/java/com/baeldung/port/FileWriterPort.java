package com.baeldung.port;

import com.baeldung.domain.Invitation;

public interface FileWriterPort {

    Invitation convertToDomainToCreateInvitation(Invitation invitation);
    Invitation convertToDomainForGettingInvitation(String invitationId);
}
