package org.baeldung.boot.repository;

import org.baeldung.boot.ApplicationTests;
import org.baeldung.boot.model.Foo;
import org.baeldung.session.exception.repository.FooRepository;
import org.hibernate.HibernateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class NoHibernateSessionTest extends ApplicationTests {
    @Autowired
    private FooRepository fooRepository;

    @Test(expected = HibernateException.class)
    public void whenSavingWithoutCurrentSession_thenThrowException() {
        Foo foo = new Foo("Exception Thrown");
        fooRepository.save(foo);
    }
}
