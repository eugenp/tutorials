package com.baeldung.memoization;

import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoizationWithMonoCacheUnitTest {

    @Test
    void givenRetrievedUser_whenTheCallToRemoteIsCache_thenReturnInvocationCountAndCompareResult() {

        MemoizationWithMonoCache memoizationWithMonoCache = new MemoizationWithMonoCache();

        Mono<User> userCall = MemoizationWithMonoCache.retrieveOneUser(1)
            .cache();
        AtomicReference<User> firstUser = new AtomicReference<>();
        AtomicReference<User> secondUser = new AtomicReference<>();

        Disposable firstUserCall = userCall.map(user -> {
                firstUser.set(user);
                return user.getName();
            })
            .subscribe();

        Disposable secondUserCall = userCall.map(user -> {
                secondUser.set(user);
                return user.getName();
            })
            .subscribe();

        assertEquals(1, memoizationWithMonoCache.getCounter());
        assertEquals(firstUser.get(), secondUser.get());

    }

}