package com.baeldung.service;

import com.baeldung.domain.Invitation;

public interface InvitationService {

    Invitation createInvitation(Invitation invitation);
    Invitation getInvitation(String invitationId);
}
