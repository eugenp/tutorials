package com.baeldung.hexagonal.contact.infrastructure;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.baeldung.hexagonal.contact.domain.model.NewContactCommand;
import com.baeldung.hexagonal.contact.domain.ports.outgoing.ContactRepository;
import com.baeldung.hexagonal.contact.infrastructure.entity.ContactEntity;

public class InMemoryContactRepositoryAdapter implements ContactRepository {

    private ConcurrentHashMap<UUID, ContactEntity> contacts = new ConcurrentHashMap<>();

    @Override
    public UUID create(NewContactCommand command) {
        ContactEntity entity = new ContactEntity(command.getName(), command.getPhoneNumber());

        UUID uuid = UUID.randomUUID();

        entity.setUUID(uuid);
        contacts.put(uuid, entity);

        return uuid;
    }
}
