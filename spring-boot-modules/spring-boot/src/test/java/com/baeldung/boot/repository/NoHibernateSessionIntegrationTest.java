package com.baeldung.boot.repository;

import com.baeldung.boot.ApplicationIntegrationTest;
import com.baeldung.demo.model.Foo;
import com.baeldung.session.exception.repository.FooRepository;
import org.hibernate.HibernateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TestPropertySource("classpath:exception-hibernate.properties")
public class NoHibernateSessionIntegrationTest extends ApplicationIntegrationTest {
    @Autowired
    private FooRepository fooRepository;

    @Test(expected = HibernateException.class)
    public void whenSavingWithoutCurrentSession_thenThrowException() {
        Foo foo = new Foo("Exception Thrown");
        fooRepository.save(foo);
    }
}
