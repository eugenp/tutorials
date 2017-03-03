package com.baeldung.mockito.java8;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ArgumentMatcherWithoutLambdaUnitTest {
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
                ArgumentMatchers.argThat(new PeterArgumentMatcher()))
             ).thenReturn(Optional.of(teacher));
        
        assertTrue(unemploymentService.personIsEntitledToUnemploymentSupport(linda));
        assertFalse(unemploymentService.personIsEntitledToUnemploymentSupport(peter));
    }

    private class PeterArgumentMatcher implements ArgumentMatcher<Person> {
        @Override
        public boolean matches(Person p) {

            if (p.getName().equals("Peter")) {
                return true;
            }
            return false;
        }
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
}
