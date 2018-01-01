package org.baeldung.persistence.domain.repository;

import org.baeldung.persistence.domain.model.Role;
import org.baeldung.persistence.domain.model.User;
import org.springframework.stereotype.Component;

import io.katharsis.repository.RelationshipRepositoryBase;



@Component
public class UserToRoleRepositoryImpl
    extends RelationshipRepositoryBase<User, Long, Role, Long> {

  public UserToRoleRepositoryImpl() {
    super(User.class, Role.class);
  }
}
