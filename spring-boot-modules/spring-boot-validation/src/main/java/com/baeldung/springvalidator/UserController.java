package com.baeldung.springvalidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserValidator userValidator;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        Errors errors = new BeanPropertyBindingResult(user, "user");
        userValidator.validate(user, errors, "create");
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        // Save the user object to the database
        return ResponseEntity.ok("User created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        Errors errors = new BeanPropertyBindingResult(user, "user");
        userValidator.validate(user, errors, "update");
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        // Update the user object in the database
        return ResponseEntity.ok("User updated successfully!");
    }
}