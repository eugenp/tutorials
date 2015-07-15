package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.ActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveUserRepository extends JpaRepository<ActiveUser, Long> {
    public void delete(ActiveUser activeUser);
    
    public ActiveUser save(ActiveUser activeUser);
}
