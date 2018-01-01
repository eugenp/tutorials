package org.baeldung.persistence.domain.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.baeldung.persistence.domain.model.Role;
import org.springframework.stereotype.Component;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryBase;
import io.katharsis.resource.list.ResourceList;



@Component
public class RoleRepositoryImpl extends ResourceRepositoryBase<Role, Long>
    implements RoleRepository {

  private Map<Long, Role> roles = new ConcurrentHashMap<>();

  public RoleRepositoryImpl() {
    super(Role.class);
  }

  @Override
  public synchronized void delete(Long id) {
    roles.remove(id);
  }

  @Override
  public synchronized <S extends Role> S save(S article) {
    roles.put(article.getId(), article);
    return article;
  }

  @Override
  public synchronized ResourceList<Role> findAll(QuerySpec querySpec) {
    return querySpec.apply(roles.values());
  }
}
