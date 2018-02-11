package com.baeldung.rxjava;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Maybe;
import io.reactivex.observers.TestObserver;

public class MaybeTest {
    private TestObserver<String> testObserver;

    @Before
    public void setUp() {
        testObserver = new TestObserver<>();
    }

    @Test
    public void whenEmitsSingleValue_observerUpdated() {
        Maybe<String> topPickToday = todaysTopPick("AGreatArticle");
        topPickToday.subscribe(testObserver);
        testObserver.assertResult("AGreatArticle");
    }

    @Test
    public void whenCompletesWithoutValue_observerGetsNoValues() {
        Maybe<String> topPickToday = todaysTopPick("");
        topPickToday.subscribe(testObserver);
        testObserver.assertNoValues();
    }

    @Test
    public void whenThrowsErrorBeforeComplete_observerSeesTheError() {
        Maybe<String> topPickToday = todaysTopPick(null);
        topPickToday.subscribe(testObserver);
        testObserver.onComplete();
        testObserver.assertErrorMessage("Error getting todays top pick.");
    }

    private Maybe<String> todaysTopPick(String analyticsResult) {
        return Maybe.create(onSubscribe -> {
            if (analyticsResult == null) {
                onSubscribe.onError(new Exception("Error getting todays top pick."));
            } else if (analyticsResult.isEmpty()) {
                onSubscribe.onComplete();
            } else {
                onSubscribe.onSuccess(analyticsResult);
            }
        });
    }
}
