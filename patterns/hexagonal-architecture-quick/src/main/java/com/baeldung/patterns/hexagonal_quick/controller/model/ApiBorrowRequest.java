package com.baeldung.patterns.hexagonal_quick.controller.model;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiBorrowRequest {
    private String username;
    private Collection<String> isbns;
}
