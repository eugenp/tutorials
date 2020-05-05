package com.baeldung.patterns.cqrs;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baeldung.patterns.cqrs.aggregates.UserAggregate;
import com.baeldung.patterns.cqrs.commands.CreateUserCommand;
import com.baeldung.patterns.cqrs.commands.UpdateUserCommand;
import com.baeldung.patterns.cqrs.projections.UserProjection;
import com.baeldung.patterns.cqrs.projectors.UserProjector;
import com.baeldung.patterns.cqrs.queries.AddressByRegionQuery;
import com.baeldung.patterns.cqrs.queries.ContactByTypeQuery;
import com.baeldung.patterns.cqrs.repository.UserReadRepository;
import com.baeldung.patterns.cqrs.repository.UserWriteRepository;
import com.baeldung.patterns.domain.Address;
import com.baeldung.patterns.domain.Contact;
import com.baeldung.patterns.domain.User;

public class Main {

    public static void main(String[] args) throws Exception {
        UserWriteRepository writeRepository = new UserWriteRepository();
        UserReadRepository readRepository = new UserReadRepository();
        UserProjector projector = new UserProjector(readRepository);
        UserAggregate userAggregate = new UserAggregate(writeRepository);
        UserProjection userProjection = new UserProjection(readRepository);

        String userId = UUID.randomUUID()
            .toString();
        User user = null;
        CreateUserCommand createUserCommand = new CreateUserCommand(userId, "Tom", "Sawyer");
        user = userAggregate.handleCreateUserCommand(createUserCommand);
        projector.project(user);

        UpdateUserCommand updateUserCommand = new UpdateUserCommand(user.getUserid(), Stream.of(new Address("New York", "NY", "10001"), new Address("Los Angeles", "CA", "90001"))
            .collect(Collectors.toSet()),
            Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"), new Contact("EMAIL", "tom.sawyer@rediff.com"))
                .collect(Collectors.toSet()));
        user = userAggregate.handleUpdateUserCommand(updateUserCommand);
        projector.project(user);

        updateUserCommand = new UpdateUserCommand(userId, Stream.of(new Address("New York", "NY", "10001"), new Address("Housten", "TX", "77001"))
            .collect(Collectors.toSet()),
            Stream.of(new Contact("EMAIL", "tom.sawyer@gmail.com"), new Contact("PHONE", "700-000-0001"))
                .collect(Collectors.toSet()));
        user = userAggregate.handleUpdateUserCommand(updateUserCommand);
        projector.project(user);

        AddressByRegionQuery addressByRegionQuery = new AddressByRegionQuery(user.getUserid(), "NY");
        System.out.println(userProjection.handle(addressByRegionQuery));

        ContactByTypeQuery contactByTypeQuery = new ContactByTypeQuery(user.getUserid(), "EMAIL");
        System.out.println(userProjection.handle(contactByTypeQuery));
    }

}
