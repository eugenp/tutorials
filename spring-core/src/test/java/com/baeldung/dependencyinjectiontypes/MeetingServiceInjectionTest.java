package com.baeldung.dependencyinjectiontypes;

import static org.junit.Assert.assertFalse;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BeanConfiguration.class)
public class MeetingServiceInjectionTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void whenGettingMeetingsFromServiceThenReturnNonEmptyCollection() {
        // given
        MeetingService meetingService = context.getBean(MeetingService.class);

        // when
        Collection<Object> meetings = meetingService.getMeetings();

        // then
        assertFalse(meetings.isEmpty());
    }

}
