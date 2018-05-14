package com.baeldung.spring.webflux;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Date;

@RestController
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {

        this.contactRepository = contactRepository;
    }

    @GetMapping("/contacts")
    public Flux<Contact> getAllContacts() {

        return contactRepository.findAll();
    }

    @PostMapping("/contacts")
    public Mono<Contact> create(@Valid @RequestBody Contact contact) {

        contact.setId(contact.getEmail());
        contact.setCreatedDate(new Date());
        return contactRepository.save(contact);
    }

    @GetMapping("/contacts/{id}")
    public Mono<ResponseEntity<Contact>> read(@PathVariable(value = "id") String contactId) {

        return contactRepository.findById(contactId).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/contacts/{id}")
    public Mono<ResponseEntity<Contact>> update(@PathVariable(value = "id") String contactId,
                                                @Valid @RequestBody Contact contact) {

        return contactRepository.findById(contactId)
                .flatMap(persistedContact -> {
                    persistedContact.setName(contact.getName());
                    persistedContact.setEmail(contact.getEmail());
                    return contactRepository.save(persistedContact);
                })
                .map(updatedContact -> new ResponseEntity<>(updatedContact, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/contacts/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable(value = "id") String contactId) {

        return contactRepository.findById(contactId)
                .flatMap(persistedContact ->
                        contactRepository.delete(persistedContact)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/contacts-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Contact> streamAll() {

        return contactRepository.findAll();
    }
}
