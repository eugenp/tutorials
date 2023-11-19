package com.baeldung.spring.reactive.customexception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    private int code;

    private String message;

    protected String reference;

}
