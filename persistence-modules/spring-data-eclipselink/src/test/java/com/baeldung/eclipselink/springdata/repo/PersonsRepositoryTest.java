package com.baeldung.eclipselink.springdata.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.eclipselink.springdata.model.Person;
import com.baeldung.eclipselink.springdata.repo.PersonsRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonsRepositoryTest {

    @Autowired
    private PersonsRepository personsRepository;

    @Test
    public void shouldAddOnePersonToDB() {
        // when
        personsRepository.save(new Person());

        // then
        assertThat(personsRepository.findAll()
            .spliterator()
            .getExactSizeIfKnown(), equalTo(1l));
    }

    @Test
    public void shouldFindOnePersonWithNameAdam() {
        // given
        Person person1 = new Person();
        person1.setFirstName("Adam");

        Person person2 = new Person();
        person2.setFirstName("Dave");

        personsRepository.save(person1);
        personsRepository.save(person2);

        // when
        Person foundPerson = personsRepository.findByFirstName("Adam");

        // then
        assertThat(foundPerson.getFirstName(), equalTo("Adam"));
        assertThat(foundPerson.getId(), notNullValue());
    }

}
