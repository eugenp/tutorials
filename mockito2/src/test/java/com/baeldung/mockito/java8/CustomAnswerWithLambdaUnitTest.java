package com.baeldung.mockito.java8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomAnswerWithLambdaUnitTest {
    @InjectMocks
    private UnemploymentServiceImpl unemploymentService;

    @Mock
    private JobService jobService;

    @Test
    public void whenPersonWithJobHistory_thenSearchReturnsValue() {
        Person peter = new Person("Peter");

        assertEquals("Teacher", unemploymentService.searchJob(peter, "").get().getTitle());
    }

    @Test
    public void whenPersonWithNoJobHistory_thenSearchReturnsEmpty() {
        Person linda = new Person("Linda");

        assertFalse(unemploymentService.searchJob(linda, "").isPresent());
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(jobService.listJobs(any(Person.class))).then((i) -> {
            return ((Person) i.getArgument(0)).getName().equals("Peter") ? Stream.of(new JobPosition("Teacher")) : Stream.empty();
        });
    }
}
