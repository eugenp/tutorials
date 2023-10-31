package com.baeldung.restvalidation.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputFieldError {
    private String field;
    private String message;

}