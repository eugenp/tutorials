package com.baeldung.hexagonal_example.infrastructure.adapters.mvc;

import com.baeldung.hexagonal_example.domain.ContactCommand;
import com.baeldung.hexagonal_example.domain.DomainException;
import com.baeldung.hexagonal_example.domain.ports.ContactApiPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/contact")
class HttpContactApiPortImpl {

    private ContactApiPort service;

    public HttpContactApiPortImpl(ContactApiPort service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ContactRequestVM contactVM) {
        try {
            ContactCommand command = ContactCommand.of(contactVM.getName(), contactVM.getEmail(), contactVM.getMobilePhone());
            ContactCreateResponse response = ContactCreateResponse.from(service.create(command));
            return ResponseEntity.ok(response);
        } catch (DomainException ex) {
            ContactErrorResponse from = ContactErrorResponse.from(ex);
            return ResponseEntity.badRequest().body(from);
        }
    }
}
