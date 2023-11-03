package com.baeldung.restvalidation.service2;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.restvalidation.response.InputFieldError;
import com.baeldung.restvalidation.response.UpdateUserResponse;

@RestController
public class UserService2 {

    @PutMapping(value = "/user2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateUserResponse> updateUser(@RequestBody @Valid User user,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {

            List<InputFieldError> fieldErrorList = bindingResult.getFieldErrors().stream()
                .map(error -> new InputFieldError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

            UpdateUserResponse updateResponse = new UpdateUserResponse(fieldErrorList);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updateResponse);
        }
        else {
            // Update logic...
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

}