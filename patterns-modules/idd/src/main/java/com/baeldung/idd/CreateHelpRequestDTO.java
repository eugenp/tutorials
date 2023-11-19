package com.baeldung.idd;

public class CreateHelpRequestDTO {

    private final HelpRequestStatus status;

    public CreateHelpRequestDTO(HelpRequestStatus status) {
        this.status = status;
    }

    public HelpRequestStatus getStatus() {
        return status;
    }

}
