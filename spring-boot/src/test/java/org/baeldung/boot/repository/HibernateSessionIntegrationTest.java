package org.baeldung.boot.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.baeldung.boot.DemoApplicationIntegrationTest;
import org.baeldung.demo.model.Foo;
import org.baeldung.demo.repository.FooRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateSessionIntegrationTest extends DemoApplicationIntegrationTest {
    @Autowired
    private FooRepository fooRepository;

    @Test
    public void whenSavingWithCurrentSession_thenThrowNoException() {
        Foo foo = new Foo("Exception Solved");
        fooRepository.save(foo);
        foo = null;
        foo = fooRepository.findByName("Exception Solved");

        assertThat(foo, notNullValue());
        assertThat(foo.getId(), is(1));
        assertThat(foo.getName(), is("Exception Solved"));
    }
}
