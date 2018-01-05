package org.baeldung.persistence.domain.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.baeldung.persistence.domain.model.Role;
import org.springframework.stereotype.Component;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;

/**
 * @author krishan.gandhi
 * The Class RoleRepositoryImpl.
 */
@Component
public class RoleRepositoryImpl extends ResourceRepositoryBase<Role, Long> implements RoleRepository {

    /** The roles. */
    private Map<Long, Role> roles = new ConcurrentHashMap<>();

    /**
     * Instantiates a new role repository impl.
     */
    public RoleRepositoryImpl() {
        super(Role.class);
    }

    /* (non-Javadoc)
     * @see io.katharsis.repository.ResourceRepositoryBase#delete(java.io.Serializable)
     */
    @Override
    public synchronized void delete(Long id) {
        roles.remove(id);
    }

    /* (non-Javadoc)
     * @see io.katharsis.repository.ResourceRepositoryBase#save(S)
     */
    @Override
    public synchronized <S extends Role> S save(S article) {
        roles.put(article.getId(), article);
        return article;
    }

    /* (non-Javadoc)
     * @see io.katharsis.repository.ResourceRepositoryV2#findAll(io.katharsis.queryspec.QuerySpec)
     */
    @Override
    public synchronized ResourceList<Role> findAll(QuerySpec querySpec) {
        return querySpec.apply(roles.values());
    }
}
