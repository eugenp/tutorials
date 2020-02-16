package com.baeldung.h2db.lazy_load_no_trans.service;

import com.baeldung.h2db.lazy_load_no_trans.entity.Document;
import com.baeldung.h2db.lazy_load_no_trans.entity.User;
import com.baeldung.h2db.lazy_load_no_trans.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceLayer {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public int countAllDocsTransactional() {
        return countAllDocs();
    }

    public int countAllDocsNonTransactional() {
        return countAllDocs();
    }

    private int countAllDocs() {
        List<Document> docs = userRepository
            .findAll().stream()
            .flatMap(user -> user.getDocs().stream())
            .collect(Collectors.toList());
        return docs.size();
    }

}
