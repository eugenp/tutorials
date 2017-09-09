package com.baeldung.rxjava;

import com.baelding.rxjava.SubjectImpl;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class SubjectTest {

    @Test
    public void givenSubjectAndTwoSubscribers_whenSubscribeOnSubjectAfterLetterC_thenSecondSubscriberBeginsToPrint() throws InterruptedException {
        String result = SubjectImpl.subjectMethod();
        String subscribers = SubjectImpl.subscriber1[0] + SubjectImpl.subscriber2[0];

        assertTrue(subscribers.equals(result));
    }
}
