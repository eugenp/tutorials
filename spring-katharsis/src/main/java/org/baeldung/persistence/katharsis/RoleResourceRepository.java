package org.baeldung.persistence.katharsis;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.ResourceRepository;

import org.baeldung.persistence.dao.RoleRepository;
import org.baeldung.persistence.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleResourceRepository implements ResourceRepository<Role, Long> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findOne(Long id, QueryParams params) {
        return roleRepository.findOne(id);
    }

    @Override
    public Iterable<Role> findAll(QueryParams params) {
        return roleRepository.findAll();
    }

    @Override
    public Iterable<Role> findAll(Iterable<Long> ids, QueryParams params) {
        return roleRepository.findAll(ids);
    }

    @Override
    public <S extends Role> S save(S entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        roleRepository.delete(id);
    }

}
