// Minor change to trigger PR comparison
package com.baeldung.validation.custommessage;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/register")
public class UserController {

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok("User registered successfully");
    }
}