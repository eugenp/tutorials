package org.baeldung.dao;

import org.springframework.stereotype.Repository;

@Repository
public class FooDao {
    public String findById(Long id) {
        return "Bazz";
    }
}
