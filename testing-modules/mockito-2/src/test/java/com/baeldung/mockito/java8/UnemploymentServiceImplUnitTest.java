package com.baeldung.mockito.java8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UnemploymentServiceImplUnitTest {
    @Mock
    private JobService jobService;

    @InjectMocks
    private UnemploymentServiceImpl unemploymentService;

    @Test
    public void givenReturnIsOfTypeOptional_whenMocked_thenValueIsEmpty() {
        Person person = new Person();

        when(jobService.findCurrentJobPosition(any(Person.class))).thenReturn(Optional.empty());

        assertTrue(unemploymentService.personIsEntitledToUnemploymentSupport(person));
    }

    @Test
    public void givenReturnIsOfTypeOptional_whenDefaultValueIsReturned_thenValueIsEmpty() {
        Person person = new Person();

        // This will fail when Mockito 1 is used
        assertTrue(unemploymentService.personIsEntitledToUnemploymentSupport(person));
    }

    @Test
    public void givenReturnIsOfTypeStream_whenMocked_thenValueIsEmpty() {
        Person person = new Person();

        when(jobService.listJobs(any(Person.class))).thenReturn(Stream.empty());

        assertFalse(unemploymentService.searchJob(person, "").isPresent());
    }

    @Test
    public void givenReturnIsOfTypeStream_whenDefaultValueIsReturned_thenValueIsEmpty() {
        Person person = new Person();

        // This will fail when Mockito 1 is used
        assertFalse(unemploymentService.searchJob(person, "").isPresent());
    }
}
