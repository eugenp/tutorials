package com.baeldung.listvsset.eager.list.simpledomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService extends com.baeldung.listvsset.Service<User> {

    public UserService(JpaRepository<User, Long> repository) {
        super(repository);
    }

    @Override
    public UserRepository getRepository() {
        return ((UserRepository) super.getRepository());
    }

}
