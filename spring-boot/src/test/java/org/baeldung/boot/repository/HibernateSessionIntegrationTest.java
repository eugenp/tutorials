package org.baeldung.boot.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.baeldung.boot.ApplicationIntegrationTest;
import org.baeldung.demo.model.Foo;
import org.baeldung.session.exception.repository.FooRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TestPropertySource("classpath:exception-hibernate.properties")
public class HibernateSessionIntegrationTest extends ApplicationIntegrationTest {
    @Autowired
    private FooRepository fooRepository;

    @Test
    public void whenSavingWithCurrentSession_thenThrowNoException() {
        Foo foo = new Foo("Exception Solved");
        fooRepository.save(foo);
        foo = null;
        foo = fooRepository.get(1);

        assertThat(foo, notNullValue());
        assertThat(foo.getId(), is(1));
        assertThat(foo.getName(), is("Exception Solved"));
    }
}
