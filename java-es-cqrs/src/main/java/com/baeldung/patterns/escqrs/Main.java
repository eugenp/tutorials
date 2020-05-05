package com.baeldung.patterns.escqrs;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baeldung.patterns.cqrs.commands.CreateUserCommand;
import com.baeldung.patterns.cqrs.commands.UpdateUserCommand;
import com.baeldung.patterns.cqrs.projections.UserProjection;
import com.baeldung.patterns.cqrs.queries.AddressByRegionQuery;
import com.baeldung.patterns.cqrs.queries.ContactByTypeQuery;
import com.baeldung.patterns.cqrs.repository.UserReadRepository;
import com.baeldung.patterns.domain.Address;
import com.baeldung.patterns.domain.Contact;
import com.baeldung.patterns.es.events.Event;
import com.baeldung.patterns.es.repository.EventStore;
import com.baeldung.patterns.escqrs.aggregates.UserAggregate;
import com.baeldung.patterns.escqrs.projectors.UserProjector;

public class Main {

    public static void main(String[] args) throws Exception {

        EventStore writeRepository = new EventStore();
        UserReadRepository readRepository = new UserReadRepository();
        UserProjector projector = new UserProjector(readRepository);
        UserAggregate userAggregate = new UserAggregate(writeRepository);
        UserProjection userProjection = new UserProjection(readRepository);

        String userId = UUID.randomUUID()
            .toString();
        List<Event> events = null;
        CreateUserCommand createUserCommand = new CreateUserCommand(userId, "Kumar", "Chandrakant");
        events = userAggregate.handleCreateUserCommand(createUserCommand);

        projector.project(userId, events);

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(userId, Stream.of(new Address("New York", "NY", "10001"), new Address("Los Angeles", "CA", "90001"))
            .collect(Collectors.toSet()),
            Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"), new Contact("EMAIL", "tom.sawyer@rediff.com"))
                .collect(Collectors.toSet()));
        events = userAggregate.handleUpdateUserCommand(updateUserCommand);
        projector.project(userId, events);

        updateUserCommand = new UpdateUserCommand(userId, Stream.of(new Address("New York", "NY", "10001"), new Address("Housten", "TX", "77001"))
            .collect(Collectors.toSet()),
            Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"), new Contact("PHONE", "700-000-0001"))
                .collect(Collectors.toSet()));
        events = userAggregate.handleUpdateUserCommand(updateUserCommand);
        projector.project(userId, events);

        AddressByRegionQuery addressByRegionQuery = new AddressByRegionQuery(userId, "NY");
        System.out.println(userProjection.handle(addressByRegionQuery));

        ContactByTypeQuery contactByTypeQuery = new ContactByTypeQuery(userId, "EMAIL");
        System.out.println(userProjection.handle(contactByTypeQuery));

    }

}
