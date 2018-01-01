package org.baeldung.persistence.domain.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.baeldung.persistence.domain.model.User;
import org.springframework.stereotype.Component;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

@Component
public class UserRepositoryImpl extends ResourceRepositoryBase<User, Long>
    implements UserRepository {

  private Map<Long, User> people = new ConcurrentHashMap<>();

  public UserRepositoryImpl() {
    super(User.class);
  }

  @Override
  public synchronized void delete(Long id) {
    people.remove(id);
  }

  @Override
  public synchronized <S extends User> S save(S person) {
    people.put(person.getId(), person);
    return person;
  }

  @Override
  public synchronized ResourceList<User> findAll(QuerySpec querySpec) {
    return querySpec.apply(people.values());
  }
}
