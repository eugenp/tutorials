package com.baeldung.idd;

public class UpdateHelpRequestDTO {

    private final HelpRequestStatus status;

    public UpdateHelpRequestDTO(HelpRequestStatus status) {
        this.status = status;
    }

    public HelpRequestStatus getStatus() {
        return status;
    }
}
