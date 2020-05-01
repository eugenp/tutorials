package com.baeldung.kotlin.rxkotlin

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.*
import io.reactivex.subjects.PublishSubject
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class RxKotlinTest {

    @Test
    fun whenBooleanArrayToObserver_thenBooleanObserver() {
        val observable = listOf(true, false, false).toObservable()
        observable.test().assertValues(true, false, false)
    }

    @Test
    fun whenBooleanArrayToFlowable_thenBooleanFlowable() {
        val flowable = listOf(true, false, false).toFlowable()
        flowable.buffer(2).test().assertValues(listOf(true, false), listOf(false))
    }

    @Test
    fun whenIntArrayToObserver_thenIntObserver() {
        val observable = listOf(1, 1, 2, 3).toObservable()
        observable.test().assertValues(1, 1, 2, 3)
    }

    @Test
    fun whenIntArrayToFlowable_thenIntFlowable() {
        val flowable = listOf(1, 1, 2, 3).toFlowable()
        flowable.buffer(2).test().assertValues(listOf(1, 1), listOf(2, 3))
    }

    @Test
    fun whenObservablePairToMap_thenSingleNoDuplicates() {
        val list = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("a", 4))
        val observable = list.toObservable()
        val map = observable.toMap()
        assertEquals(mapOf(Pair("a", 4), Pair("b", 2), Pair("c", 3)), map.blockingGet())
    }

    @Test
    fun whenObservablePairToMap_thenSingleWithDuplicates() {
        val list = listOf(Pair("a", 1), Pair("b", 2), Pair("c", 3), Pair("a", 4))
        val observable = list.toObservable()
        val map = observable.toMultimap()
        assertEquals(
                mapOf(Pair("a", listOf(1, 4)), Pair("b", listOf(2)), Pair("c", listOf(3))),
                map.blockingGet())
    }

    @Test
    fun whenMergeAll_thenStream() {
        val subject = PublishSubject.create<Observable<String>>()
        val observable = subject.mergeAll()
        val testObserver = observable.test()
        subject.onNext(Observable.just("first", "second"))
        testObserver.assertValues("first", "second")
        subject.onNext(Observable.just("third", "fourth"))
        subject.onNext(Observable.just("fifth"))
        testObserver.assertValues("first", "second", "third", "fourth", "fifth")
    }

    @Test
    fun whenConcatAll_thenStream() {
        val subject = PublishSubject.create<Observable<String>>()
        val observable = subject.concatAll()
        val testObserver = observable.test()
        subject.onNext(Observable.just("first", "second"))
        testObserver.assertValues("first", "second")
        subject.onNext(Observable.just("third", "fourth"))
        subject.onNext(Observable.just("fifth"))
        testObserver.assertValues("first", "second", "third", "fourth", "fifth")
    }

    @Test
    fun whenSwitchLatest_thenStream() {
        val subject = PublishSubject.create<Observable<String>>()
        val observable = subject.switchLatest()
        val testObserver = observable.test()
        subject.onNext(Observable.just("first", "second"))
        testObserver.assertValues("first", "second")
        subject.onNext(Observable.just("third", "fourth"))
        subject.onNext(Observable.just("fifth"))
        testObserver.assertValues("first", "second", "third", "fourth", "fifth")
    }

    @Test
    fun whenMergeAllMaybes_thenObservable() {
        val subject = PublishSubject.create<Maybe<Int>>()
        val observable = subject.mergeAllMaybes()
        val testObserver = observable.test()
        subject.onNext(Maybe.just(1))
        subject.onNext(Maybe.just(2))
        subject.onNext(Maybe.empty())
        testObserver.assertValues(1, 2)
        subject.onNext(Maybe.error(Exception("")))
        subject.onNext(Maybe.just(3))
        testObserver.assertValues(1, 2).assertError(Exception::class.java)
    }

    @Test
    fun whenMerge_thenStream() {
        val observables = mutableListOf(Observable.just("first", "second"))
        val observable = observables.merge()
        observables.add(Observable.just("third", "fourth"))
        observables.add(Observable.error(Exception("e")))
        observables.add(Observable.just("fifth"))

        observable.test().assertValues("first", "second", "third", "fourth").assertError(Exception::class.java)
    }

    @Test
    fun whenMergeDelayError_thenStream() {
        val observables = mutableListOf<Observable<String>>(Observable.error(Exception("e1")))
        val observable = observables.mergeDelayError()
        observables.add(Observable.just("1", "2"))
        observables.add(Observable.error(Exception("e2")))
        observables.add(Observable.just("3"))

        observable.test().assertValues("1", "2", "3").assertError(Exception::class.java)
    }

    @Test
    fun whenCast_thenUniformType() {
        val observable = Observable.just<Number>(1, 1, 2, 3)
        observable.cast<Int>().test().assertValues(1, 1, 2, 3)
    }

    @Test
    fun whenOfType_thenFilter() {
        val observable = Observable.just(1, "and", 2, "and")
        observable.ofType<Int>().test().assertValues(1, 2)
    }

    @Test
    fun whenFunction_thenCompletable() {
        var value = 0
        val completable = { value = 3 }.toCompletable()
        assertFalse(completable.test().isCancelled)
        assertEquals(3, value)
    }

    @Test
    fun whenHelper_thenMoreIdiomaticKotlin() {
        val zipWith = Observable.just(1).zipWith(Observable.just(2)) { a, b -> a + b }
        zipWith.subscribeBy(onNext = { println(it) })
        val zip = Observables.zip(Observable.just(1), Observable.just(2)) { a, b -> a + b }
        zip.subscribeBy(onNext = { println(it) })
        val zipOrig = Observable.zip(Observable.just(1), Observable.just(2), BiFunction<Int, Int, Int> { a, b -> a + b })
        zipOrig.subscribeBy(onNext = { println(it) })
    }
}
