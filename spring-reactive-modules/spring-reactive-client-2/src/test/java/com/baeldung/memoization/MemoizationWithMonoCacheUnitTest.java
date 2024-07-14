package com.baeldung.memoization;

import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoizationWithMonoCacheUnitTest {

    @Test
    void givenRetrievedUser_whenTheCallToRemoteIsNotCache_thenReturnInvocationCountAndCompareResult() {

        MemoizationWithMonoCache memoizationWithMonoCache = new MemoizationWithMonoCache();

        Mono<User> retrieveOneUser = memoizationWithMonoCache.retrieveOneUser(1);
        AtomicReference<User> firstUser = new AtomicReference<>();
        AtomicReference<User> secondUser = new AtomicReference<>();

        Disposable firstUserCall = retrieveOneUser.map(user -> {
                firstUser.set(user);
                return user.getName();
            })
            .subscribe();

        Disposable secondUserCall = retrieveOneUser.map(user -> {
                secondUser.set(user);
                return user.getName();
            })
            .subscribe();

        assertEquals(2, memoizationWithMonoCache.getCounter());
        assertEquals(firstUser.get(), secondUser.get());

    }

    @Test
    void givenRetrievedUser_whenTheCallToRemoteIsCache_thenReturnInvocationCountAndCompareResult() {

        MemoizationWithMonoCache memoizationWithMonoCache = new MemoizationWithMonoCache();

        Mono<User> retrieveOneUser = memoizationWithMonoCache.retrieveOneUser(1)
            .cache();
        AtomicReference<User> firstUser = new AtomicReference<>();
        AtomicReference<User> secondUser = new AtomicReference<>();

        Disposable firstUserCall = retrieveOneUser.map(user -> {
                firstUser.set(user);
                return user.getName();
            })
            .subscribe();

        Disposable secondUserCall = retrieveOneUser.map(user -> {
                secondUser.set(user);
                return user.getName();
            })
            .subscribe();

        assertEquals(1, memoizationWithMonoCache.getCounter());
        assertEquals(firstUser.get(), secondUser.get());

    }

    @Test
    void givenRetrievedUser_whenTheCallToRemoteIsCacheWithDuration_thenReturnInvocationCountAndCompareResult() {

        MemoizationWithMonoCache memoizationWithMonoCache = new MemoizationWithMonoCache();

        Mono<User> retrieveOneUser = memoizationWithMonoCache.retrieveOneUser(1)
            .cache(Duration.ofMinutes(5));
        AtomicReference<User> firstUser = new AtomicReference<>();
        AtomicReference<User> secondUser = new AtomicReference<>();

        Disposable firstUserCall = retrieveOneUser.map(user -> {
                firstUser.set(user);
                return user.getName();
            })
            .subscribe();

        Disposable secondUserCall = retrieveOneUser.map(user -> {
                secondUser.set(user);
                return user.getName();
            })
            .subscribe();

        assertEquals(1, memoizationWithMonoCache.getCounter());
        assertEquals(firstUser.get(), secondUser.get());

    }

}
