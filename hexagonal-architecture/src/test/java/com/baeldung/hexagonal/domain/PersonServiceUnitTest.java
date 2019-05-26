package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.domain.dto.CreatePersonDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceUnitTest {

    @InjectMocks private PersonService personService;

    @Mock private PersonRepositoryPort repositoryPort;

    @Test
    public void whenSavingPerson_thenRepositoryIsCalled() {
        //Given
        final CreatePersonDto personDto = new CreatePersonDto("name");
        final UUID id = UUID.randomUUID();
        //When
        personService.save(id, personDto);
        //Then
        verify(repositoryPort, times(1)).save(new Person(id, personDto.getName()));
    }

    @Test
    public void whenGettingById_thenPersonIsObtained() {
        //Given
        final UUID id = UUID.randomUUID();
        final Person person = new Person(id, "name");

        when(repositoryPort.findById(id)).thenReturn(Optional.of(person));
        //When
        final Person obtained = personService.getById(id);
        //Then
        Assert.assertEquals(person, obtained);
    }

    @Test(expected = RuntimeException.class)
    public void whenGettingById_thenPersonNotFound() {
        //Given
        final UUID id = UUID.randomUUID();

        when(repositoryPort.findById(id)).thenReturn(Optional.empty());
        //When
        personService.getById(id);
        //Then exception is thrown
    }
}