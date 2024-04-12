package com.baeldung.mockito.java8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceUnitTest {
    @Mock
    private JobService jobService;

    @Test
    public void givenDefaultMethod_whenCallRealMethod_thenNoExceptionIsRaised() {
        Person person = new Person();

        when(jobService.findCurrentJobPosition(person)).thenReturn(Optional.of(new JobPosition()));
        doCallRealMethod().when(jobService).assignJobPosition(Mockito.any(Person.class), Mockito.any(JobPosition.class));

        assertFalse(jobService.assignJobPosition(person, new JobPosition()));
    }

    @Test
    public void givenReturnIsOfTypeOptional_whenDefaultValueIsReturned_thenValueIsEmpty() {
        Person person = new Person();

        when(jobService.findCurrentJobPosition(person)).thenReturn(Optional.empty());
        doCallRealMethod().when(jobService).assignJobPosition(Mockito.any(Person.class), Mockito.any(JobPosition.class));

        assertTrue(jobService.assignJobPosition(person, new JobPosition()));
    }
}
