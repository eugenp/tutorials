package org.baeldung.persistence.domain.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.baeldung.persistence.domain.model.User;
import org.springframework.stereotype.Component;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

/**
 * @author krishan.gandhi
 * The Class UserRepositoryImpl.
 */
@Component
public class UserRepositoryImpl extends ResourceRepositoryBase<User, Long> implements UserRepository {

    /** The people. */
    private Map<Long, User> people = new ConcurrentHashMap<>();

    /**
     * Instantiates a new user repository impl.
     */
    public UserRepositoryImpl() {
        super(User.class);
    }

    /* (non-Javadoc)
     * @see io.katharsis.repository.ResourceRepositoryBase#delete(java.io.Serializable)
     */
    @Override
    public synchronized void delete(Long id) {
        people.remove(id);
    }

    /* (non-Javadoc)
     * @see io.katharsis.repository.ResourceRepositoryBase#save(S)
     */
    @Override
    public synchronized <S extends User> S save(S person) {
        people.put(person.getId(), person);
        return person;
    }

    /* (non-Javadoc)
     * @see io.katharsis.repository.ResourceRepositoryV2#findAll(io.katharsis.queryspec.QuerySpec)
     */
    @Override
    public synchronized ResourceList<User> findAll(QuerySpec querySpec) {
        return querySpec.apply(people.values());
    }
}
