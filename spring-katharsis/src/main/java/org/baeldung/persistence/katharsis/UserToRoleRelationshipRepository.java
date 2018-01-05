package org.baeldung.persistence.katharsis;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.RelationshipRepository;

import java.util.HashSet;
import java.util.Set;

import org.baeldung.persistence.dao.RoleRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.Role;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserToRoleRelationshipRepository implements RelationshipRepository<User, Long, Role, Long> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void setRelation(User User, Long roleId, String fieldName) {
        // not for many-to-many
    }

    @Override
    public void setRelations(User user, Iterable<Long> roleIds, String fieldName) {
        final Set<Role> roles = new HashSet<Role>();
        roles.addAll(roleRepository.findAll(roleIds));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void addRelations(User user, Iterable<Long> roleIds, String fieldName) {
        final Set<Role> roles = user.getRoles();
        roles.addAll(roleRepository.findAll(roleIds));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void removeRelations(User user, Iterable<Long> roleIds, String fieldName) {
        final Set<Role> roles = user.getRoles();
        roles.removeAll(roleRepository.findAll(roleIds));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public Role findOneTarget(Long sourceId, String fieldName, QueryParams QueryParams) {
        // not for many-to-many
        return null;
    }

    @Override
    public Iterable<Role> findManyTargets(Long sourceId, String fieldName, QueryParams QueryParams) {
        final User user = userRepository.findOne(sourceId);
        return user.getRoles();
    }
}
