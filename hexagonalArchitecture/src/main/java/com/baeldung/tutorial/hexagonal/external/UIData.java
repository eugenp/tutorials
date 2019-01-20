package com.baeldung.tutorial.hexagonal.external;

import lombok.Getter;

public class UIData {

    @Getter
    private boolean subscriptionPaid = false;

    @Getter
    private String userName = "baeldung";

    @Getter
    private String userEmail = "baeldung@baeldung.com";

    @Getter
    private String userAddress = "Address of the user";
}
