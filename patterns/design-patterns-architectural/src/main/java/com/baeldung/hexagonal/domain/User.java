package com.baeldung.hexagonal.domain;

import lombok.Data;

@Data
public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private MemeberStatus status;
    private int rewardedpoints;
}
