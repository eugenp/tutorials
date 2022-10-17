package com.baeldung.mockito.java8;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomAnswerWithoutLambdaUnitTest {
    
    private class PersonAnswer implements Answer<Stream<JobPosition>> {

        @Override
        public Stream<JobPosition> answer(InvocationOnMock invocation) throws Throwable {
            Person person = invocation.getArgument(0);
            
            if(person.getName().equals("Peter")) {
                return Stream.<JobPosition>builder().add(new JobPosition("Teacher")).build();
            } 
            
            return Stream.empty();
        }
    }
    
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
        when(jobService.listJobs(any(Person.class))).then(new PersonAnswer());
    }
}
