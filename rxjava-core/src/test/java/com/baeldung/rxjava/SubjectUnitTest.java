package com.baeldung.rxjava;

import org.junit.Test;
import rx.subjects.PublishSubject;

import static junit.framework.Assert.assertTrue;

public class SubjectUnitTest {

    @Test
    public void givenSubjectAndTwoSubscribers_whenSubscribeOnSubject_thenSubscriberBeginsToAdd() {
        PublishSubject<Integer> subject = PublishSubject.create();

        subject.subscribe(SubjectImpl.getFirstObserver());
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);

        subject.subscribe(SubjectImpl.getSecondObserver());
        subject.onNext(4);
        subject.onCompleted();

        assertTrue(SubjectImpl.subscriber1 + SubjectImpl.subscriber2 == 14);
    }
}
