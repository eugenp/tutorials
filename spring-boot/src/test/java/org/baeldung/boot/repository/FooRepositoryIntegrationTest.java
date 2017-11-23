package org.baeldung.boot.repository;

import static org.junit.Assert.assertThat;

import org.baeldung.boot.DemoApplicationIntegrationTest;
import org.baeldung.demo.model.Foo;
import org.baeldung.demo.repository.FooRepository;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FooRepositoryIntegrationTest extends DemoApplicationIntegrationTest {
    @Autowired
    private FooRepository fooRepository;

    @Before
    public void setUp() {
        fooRepository.save(new Foo("Foo"));
        fooRepository.save(new Foo("Bar"));
    }

    @Test
    public void testFindByName() {
        Foo foo = fooRepository.findByName("Bar");
        assertThat(foo, notNullValue());
        assertThat(foo.getId(), is(2));
    }

}
