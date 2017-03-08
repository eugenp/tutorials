package org.baeldung.boot.components;

import org.baeldung.boot.model.Foo;
import org.baeldung.boot.repository.FooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FooService {

    @Autowired
    private FooRepository fooRepository;
    
    public Foo getFooWithId(Integer id) throws Exception {
        return fooRepository.findOne(id);
    }
    
    public Foo getFooWithName(String name) {
        return fooRepository.findByName(name);
    }
}