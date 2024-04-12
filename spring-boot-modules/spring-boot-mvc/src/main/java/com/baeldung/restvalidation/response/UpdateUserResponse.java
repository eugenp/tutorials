package com.baeldung.restvalidation.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserResponse {

    private List<InputFieldError> fieldErrors;

}
