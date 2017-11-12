package com.baeldung.mockito.java8;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ArgumentMatcherWithLambdaUnitTest {

    @InjectMocks
    private UnemploymentServiceImpl unemploymentService;
    
    @Mock
    private JobService jobService;

    @Test
    public void whenPersonWithJob_thenIsNotEntitled() {
        Person peter = new Person("Peter");
        Person linda = new Person("Linda");
        
        JobPosition teacher = new JobPosition("Teacher");

        when(jobService.findCurrentJobPosition(
                ArgumentMatchers.argThat((p) -> p.getName().equals("Peter")))
             ).thenReturn(Optional.of(teacher));
        
        assertTrue(unemploymentService.personIsEntitledToUnemploymentSupport(linda));
        assertFalse(unemploymentService.personIsEntitledToUnemploymentSupport(peter));
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
}
