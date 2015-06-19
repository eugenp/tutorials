package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.ActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveUserRepository extends JpaRepository<ActiveUser, Long>, ActiveUserRepository0 {
    @Override
    public void delete(ActiveUser activeUser);
    
    @Override
    public ActiveUser save(ActiveUser activeUser);
}
