package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.entities.InMemoryUserEntity;
import com.baeldung.hexagonal.ports.UserDatabasePort;
import com.baeldung.hexagonal.repositories.InMemoryUserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({ "SpellCheckingInspection", "unused", "WeakerAccess" })
public class InMemoryUserAdapter implements UserDatabasePort {

    public InMemoryUserRepository repository = new InMemoryUserRepository();

    public void save(User user) {

        InMemoryUserEntity entity = new InMemoryUserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setHandles(user.getHandles()
                              .stream()
                              .map(handle -> {
                                  Map<String, String> userHandle = new HashMap<>();
                                  userHandle.put("type", handle.getType().toString());
                                  userHandle.put("handle", handle.getHandle());
                                  return userHandle;
                              }).collect(Collectors.toList()));

        repository.save(entity);

    }

    // other operations
}
