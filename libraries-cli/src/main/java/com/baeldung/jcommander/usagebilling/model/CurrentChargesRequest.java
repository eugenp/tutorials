package com.baeldung.jcommander.usagebilling.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Getter
public class CurrentChargesRequest {

    private String customerId;
    private List<String> subscriptionIds;
    private boolean itemized;
}
