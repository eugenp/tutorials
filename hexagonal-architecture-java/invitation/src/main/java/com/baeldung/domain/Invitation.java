package com.baeldung.domain;

//@Data
//lombok is not working. will integrate it later
public class Invitation {

    private String invitationMessage;
    private String invitationDate;

    public String getInvitationMessage() {
        return invitationMessage;
    }

    public void setInvitationMessage(String invitationMessage) {
        this.invitationMessage = invitationMessage;
    }

    public String getInvitationDate() {
        return invitationDate;
    }

    public void setInvitationDate(String invitationDate) {
        this.invitationDate = invitationDate;
    }

}
