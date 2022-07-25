package com.baeldung.mockito.java8;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ArgumentMatcherWithoutLambdaUnitTest {

    private class PeterArgumentMatcher implements ArgumentMatcher<Person> {

        @Override
        public boolean matches(Person p) {
            return p
              .getName()
              .equals("Peter");
        }
    }

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
}
