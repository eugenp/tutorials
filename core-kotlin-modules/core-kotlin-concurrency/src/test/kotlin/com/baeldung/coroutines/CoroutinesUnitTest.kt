package com.baeldung.coroutines

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class CoroutinesTest {

    @Test
    fun givenBuildSequence_whenTakeNElements_thenShouldReturnItInALazyWay() {
        //given
        val fibonacciSeq = sequence {
            var a = 0
            var b = 1

            yield(1)

            while (true) {
                yield(a + b)

                val tmp = a + b
                a = b
                b = tmp
            }
        }

        //when
        val res = fibonacciSeq.take(5).toList()

        //then
        assertEquals(res, listOf(1, 1, 2, 3, 5))
    }

    @Test
    fun givenLazySeq_whenTakeNElements_thenShouldReturnAllElements() {
        //given
        val lazySeq = sequence {
            print("START ")
            for (i in 1..5) {
                yield(i)
                print("STEP ")
            }
            print("END")
        }
        //when
        val res = lazySeq.take(10).toList()

        //then
        assertEquals(res, listOf(1, 2, 3, 4, 5))
    }

    @Test
    fun givenAsyncCoroutine_whenStartIt_thenShouldExecuteItInTheAsyncWay() {
        //given
        val res = mutableListOf<String>()

        //when
        runBlocking {
            val promise = launch(Dispatchers.Default) { expensiveComputation(res) }
            res.add("Hello,")
            promise.join()
        }

        //then
        assertEquals(res, listOf("Hello,", "word!"))
    }


    suspend fun expensiveComputation(res: MutableList<String>) {
        delay(1000L)
        res.add("word!")
    }

    @Test
    fun givenHugeAmountOfCoroutines_whenStartIt_thenShouldExecuteItWithoutOutOfMemory() {
        runBlocking<Unit> {
            //given
            val counter = AtomicInteger(0)
            val numberOfCoroutines = 100_000

            //when
            val jobs = List(numberOfCoroutines) {
                launch(Dispatchers.Default) {
                    delay(1L)
                    counter.incrementAndGet()
                }
            }
            jobs.forEach { it.join() }

            //then
            assertEquals(counter.get(), numberOfCoroutines)
        }
    }

    @Test
    fun givenCancellableJob_whenRequestForCancel_thenShouldQuit() {
        runBlocking<Unit> {
            //given
            val job = launch(Dispatchers.Default) {
                while (isActive) {
                    //println("is working")
                }
            }

            delay(1300L)

            //when
            job.cancel()

            //then cancel successfully

        }
    }

    @Test(expected = CancellationException::class)
    fun givenAsyncAction_whenDeclareTimeout_thenShouldFinishWhenTimedOut() {
        runBlocking<Unit> {
            withTimeout(1300L) {
                repeat(1000) { i ->
                    println("Some expensive computation $i ...")
                    delay(500L)
                }
            }
        }
    }

    @Test
    fun givenHaveTwoExpensiveAction_whenExecuteThemAsync_thenTheyShouldRunConcurrently() {
        runBlocking<Unit> {
            val delay = 1000L
            val time = measureTimeMillis {
                //given
                val one = async(Dispatchers.Default) { someExpensiveComputation(delay) }
                val two = async(Dispatchers.Default) { someExpensiveComputation(delay) }

                //when
                runBlocking {
                    one.await()
                    two.await()
                }
            }

            //then
            assertTrue(time < delay * 2)
        }
    }

    @Test
    fun givenTwoExpensiveAction_whenExecuteThemLazy_thenTheyShouldNotConcurrently() {
        runBlocking<Unit> {
            val delay = 1000L
            val time = measureTimeMillis {
                //given
                val one = async(Dispatchers.Default, CoroutineStart.LAZY) { someExpensiveComputation(delay) }
                val two = async(Dispatchers.Default, CoroutineStart.LAZY) { someExpensiveComputation(delay) }

                //when
                runBlocking {
                    one.await()
                    two.await()
                }
            }

            //then
            assertTrue(time > delay * 2)
        }
    }

    suspend fun someExpensiveComputation(delayInMilliseconds: Long) {
        delay(delayInMilliseconds)
    }


}
