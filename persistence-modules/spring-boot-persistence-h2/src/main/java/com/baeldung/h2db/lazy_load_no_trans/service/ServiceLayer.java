package com.baeldung.h2db.lazy_load_no_trans.service;

import com.baeldung.h2db.lazy_load_no_trans.entity.User;
import com.baeldung.h2db.lazy_load_no_trans.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ServiceLayer {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public long countAllDocsTransactional() {
        return countAllDocs();
    }

    public long countAllDocsNonTransactional() {
        return countAllDocs();
    }

    private long countAllDocs() {
        return userRepository.findAll()
            .stream()
            .map(User::getDocs)
            .mapToLong(Collection::size)
            .sum();
    }

}
