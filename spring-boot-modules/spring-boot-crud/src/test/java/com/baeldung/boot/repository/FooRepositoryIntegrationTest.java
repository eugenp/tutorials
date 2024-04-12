package com.baeldung.boot.repository;

import com.baeldung.demo.DemoApplicationIntegrationTest;
import com.baeldung.demo.model.Foo;
import com.baeldung.demo.repository.FooRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

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
        assertThat(foo.getId(), notNullValue());
        assertThat(foo.getName(), is("Bar"));
    }

}
