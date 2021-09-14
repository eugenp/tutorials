package com.baeldung.boot.hexagonal.application.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.io.Serializable;

@Data
@AllArgsConstructor
@With
@NoArgsConstructor
public class Response implements Serializable {
    private static final long serialVersionUID = 4728425002711622613L;
    private int code;
    private String message;
}
