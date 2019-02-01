package unittest;


import com.baeldung.springboot.entity.Person;
import com.baeldung.springboot.model.dto.PersonDto;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class PersonDtoUnitTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testDto_toModel() {

        PersonDto personDto = new PersonDto();

        personDto.setFirstName("FirstJava");
        personDto.setLastName("LastJava");
        personDto.setGender("M");
        personDto.setPostCode("CW2ZZZ");
        personDto.setEmail("email@email.com");

        Person personEntity = modelMapper.map(personDto, Person.class);
        assertEquals(personDto.getFirstName(), personEntity.getFirstName());
        assertEquals(personDto.getLastName(), personEntity.getLastName());
        assertEquals(personDto.getGender(), personEntity.getGender());
        assertEquals(personDto.getEmail(), personEntity.getEmail());
    }

}