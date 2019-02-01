package unittest;

import com.baeldung.springboot.entity.Person;
import com.baeldung.springboot.model.PersonResponse;
import com.baeldung.springboot.model.dto.PersonDto;
import com.baeldung.springboot.respository.PersonRepository;
import com.baeldung.springboot.service.PersonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.Silent.class)
public class PersonServiceImplUnitTest {

    @InjectMocks
    PersonServiceImpl personService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    PersonRepository personRepository;


    @Test
    public void testGivenDbService_whenCreateAPerson_thenPersist() throws Exception {

        Person person = aPersonEntity();
        PersonDto personDto = aPersonDto();

        given(modelMapper.map(personDto, Person.class)).willReturn(person);
        given(personRepository.save(person)).willReturn(person);

        PersonResponse personResponse = personService.createPerson(personDto);

        assertThat(personResponse).hasFieldOrProperty("location").isNotNull();
    }

    private PersonDto aPersonDto() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("FirstJava");
        personDto.setLastName("LastJava");
        personDto.setGender("M");
        personDto.setPostCode("CW2ZZZ");
        personDto.setEmail("email@email.com");
        return personDto;
    }

    private Person aPersonEntity() {
        Person person = new Person();
        person.setId(123L);
        person.setFirstName("FirstJava");
        person.setLastName("LastJava");
        person.setGender("M");
        person.setPostCode("CW2ZZZ");
        person.setEmail("email@email.com");
        return person;
    }


}
