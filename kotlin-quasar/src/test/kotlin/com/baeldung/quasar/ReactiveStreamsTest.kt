package com.baeldung.quasar

import co.paralleluniverse.fibers.Suspendable
import co.paralleluniverse.kotlin.fiber
import co.paralleluniverse.strands.channels.Channels
import co.paralleluniverse.strands.channels.Topic
import co.paralleluniverse.strands.channels.reactivestreams.ReactiveStreams
import org.junit.Test
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

class ReactiveStreamsTest {
    companion object {
        private val LOG = LoggerFactory.getLogger(ReactiveStreamsTest::class.java)
    }

    @Test
    fun publisher() {
        val inputChannel = Channels.newChannel<String>(1);

        val publisher = ReactiveStreams.toPublisher(inputChannel)
        publisher.subscribe(object : Subscriber<String> {
            @Suspendable
            override fun onComplete() {
                LOG.info("onComplete")
            }

            @Suspendable
            override fun onSubscribe(s: Subscription) {
                LOG.info("onSubscribe: {}", s)
                s.request(2)
            }

            @Suspendable
            override fun onNext(t: String?) {
                LOG.info("onNext: {}", t)
            }

            @Suspendable
            override fun onError(t: Throwable?) {
                LOG.info("onError: {}", t)
            }
        })

        inputChannel.send("Hello")
        inputChannel.send("World")

        TimeUnit.SECONDS.sleep(1)

        inputChannel.close()
    }

    @Test
    fun publisherTopic() {
        val inputTopic = Topic<String>()

        val publisher = ReactiveStreams.toPublisher(inputTopic)
        publisher.subscribe(object : Subscriber<String> {
            @Suspendable
            override fun onComplete() {
                LOG.info("onComplete 1")
            }

            @Suspendable
            override fun onSubscribe(s: Subscription) {
                LOG.info("onSubscribe 1: {}", s)
                s.request(2)
            }

            @Suspendable
            override fun onNext(t: String?) {
                LOG.info("onNext 1: {}", t)
            }

            @Suspendable
            override fun onError(t: Throwable?) {
                LOG.info("onError 1: {}", t)
            }
        })
        publisher.subscribe(object : Subscriber<String> {
            @Suspendable
            override fun onComplete() {
                LOG.info("onComplete 2")
            }

            @Suspendable
            override fun onSubscribe(s: Subscription) {
                LOG.info("onSubscribe 2: {}", s)
                s.request(2)
            }

            @Suspendable
            override fun onNext(t: String?) {
                LOG.info("onNext 2: {}", t)
            }

            @Suspendable
            override fun onError(t: Throwable?) {
                LOG.info("onError 2: {}", t)
            }
        })

        inputTopic.send("Hello")
        inputTopic.send("World")

        TimeUnit.SECONDS.sleep(1)

        inputTopic.close()
    }

    @Test
    fun subscribe() {
        val inputChannel = Channels.newChannel<String>(10);
        val publisher = ReactiveStreams.toPublisher(inputChannel)

        val channel = ReactiveStreams.subscribe(10, Channels.OverflowPolicy.THROW, publisher)

        fiber @Suspendable {
            while (!channel.isClosed) {
                val message = channel.receive()
                LOG.info("Received: {}", message)
            }
            LOG.info("Stopped receiving messages")
        }

        inputChannel.send("Hello")
        inputChannel.send("World")

        TimeUnit.SECONDS.sleep(1)

        inputChannel.close()
    }
}
