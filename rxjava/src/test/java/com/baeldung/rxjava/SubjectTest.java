package com.baeldung.rxjava;

import com.baelding.rxjava.SubjectImpl;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class SubjectTest {

    @Test
    public void givenSubjectAndTwoSubscribers_whenSubscribeOnSubjectAfterLetterC_thenSecondSubscriberBeginsToPrint() throws InterruptedException {
        SubjectImpl.subjectMethod();

        assertTrue(SubjectImpl.subscriber1[0].equals("abcdefg"));
        assertTrue(SubjectImpl.subscriber2[0].equals("efg"));
    }
}
