package com.baeldung.lazy

import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.assertEquals

class LazyUnitTest {
    @Test
    fun givenLazyValue_whenGetIt_thenShouldInitializeItOnlyOnce() {
        //given
        val numberOfInitializations: AtomicInteger = AtomicInteger()
        val lazyValue: ClassWithHeavyInitialization by lazy {
            numberOfInitializations.incrementAndGet()
            ClassWithHeavyInitialization()
        }
        //when
        println(lazyValue)
        println(lazyValue)

        //then
        assertEquals(numberOfInitializations.get(), 1)
    }

    @Test
    fun givenLazyValue_whenGetItUsingPublication_thenCouldInitializeItMoreThanOnce() {
        //given
        val numberOfInitializations: AtomicInteger = AtomicInteger()
        val lazyValue: ClassWithHeavyInitialization by lazy(LazyThreadSafetyMode.PUBLICATION) {
            numberOfInitializations.incrementAndGet()
            ClassWithHeavyInitialization()
        }
        val executorService = Executors.newFixedThreadPool(2)
        val countDownLatch = CountDownLatch(1)
        //when
        executorService.submit { countDownLatch.await(); println(lazyValue) }
        executorService.submit { countDownLatch.await(); println(lazyValue) }
        countDownLatch.countDown()

        //then
        executorService.shutdown()
        executorService.awaitTermination(5, TimeUnit.SECONDS)
        //assertEquals(numberOfInitializations.get(), 2)
    }

    class ClassWithHeavyInitialization {

    }


    lateinit var a: String
    @Test
    fun givenLateInitProperty_whenAccessItAfterInit_thenPass() {
        //when
        a = "it"
        println(a)

        //then not throw
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun givenLateInitProperty_whenAccessItWithoutInit_thenThrow() {
        //when
        println(a)
    }
}