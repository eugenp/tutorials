package com.baeldung;

import com.baeldung.model.Person;
import com.baeldung.model.Post;
import com.baeldung.repository.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfig.class)
public class PersonUnitTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void whenFind_thenReturnCorrectListSize() {
        final Iterable<Person> listIterable = personRepository.find();
        final List<Person> list = new ArrayList<>();
        listIterable.forEach(list::add);
        assertEquals(2, list.size());
        assertEquals("John", list.get(0).getName());
    }

    @Test
    public void whenFindAll_thenReturnCorrectListSize() {
        final Iterable<Person> listIterable = personRepository.findAll();
        final List<Person> list = new ArrayList<>();
        listIterable.forEach(list::add);
        assertEquals(3, list.size());
    }

    @Test
    public void whenFindPostsByPerson_thenReturnCorrectListSize() {
        final Iterable<Post> listIterable = personRepository.findPostsByPerson();
        final List<Post> list = new ArrayList<>();
        listIterable.forEach(list::add);
        assertEquals(7, list.size());
    }

}
