package com.baeldung.jcommander.usagebilling.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class CurrentChargesRequest {

    private String customerId;
    private List<String> subscriptionIds;
    private boolean itemized;
}
