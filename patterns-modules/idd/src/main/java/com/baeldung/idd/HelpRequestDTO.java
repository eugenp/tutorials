package com.baeldung.idd;

public class HelpRequestDTO {
    private HelpRequestStatus status;

    public HelpRequestStatus getStatus() {
        return status;
    }

    public HelpRequestDTO(HelpRequestStatus status) {
        this.status = status;
    }
}
